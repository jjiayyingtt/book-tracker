package tracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tracker.logic.parser.CliSyntax.*;
import static tracker.logic.parser.CliSyntax.PREFIX_DATESTARTED;
import static tracker.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import tracker.commons.core.Messages;
import tracker.commons.core.index.Index;
import tracker.commons.util.CollectionUtil;
import tracker.logic.commands.exceptions.CommandException;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.Model;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * Edits the details of an existing book in the book tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the book identified "
            + "by the index number used in the displayed book list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_PROGRESS + "PROGRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AUTHOR + "Mark Haddon "
            + PREFIX_NOTE + "not so great";

    public static final String MESSAGE_EDIT_BOOK_SUCCESS = "Edited Book: %1$s";
    public static final String MESSAGE_EDIT_CATEGORY_CHANGE = "Edited Book: %1$s\n"
            + "Because of a change in category, other fields might have been changed. "
            + "(Date Started, Date Finished and Rating)";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book tracker.";

    public static final String MESSAGE_CONSTRAINTS_FOR_DATESTARTED=
            "Only read or currently reading books can have date started.";
    public static final String MESSAGE_CONSTRAINTS_FOR_DATEFINISHED =
            "Only read books can have date finished.";

    public static final String MESSAGE_CONSTRAINTS_FOR_DATE_STARTED_AFTER_FINISHED =
            "Date started cannot be after date finished.";
    public static final String MESSAGE_CONSTRAINTS_FOR_RATING =
            "Only read books can have rating.";

    private final Index index;
    private final EditBookDescriptor editBookDescriptor;

    private final boolean categoryChanged;

    /**
     * @param index of the book in the filtered book list to edit
     * @param editBookDescriptor details to edit the book with
     */
    public EditCommand(Index index, EditBookDescriptor editBookDescriptor, boolean categoryChanged) {
        requireNonNull(index);
        requireNonNull(editBookDescriptor);

        this.index = index;
        this.editBookDescriptor = new EditBookDescriptor(editBookDescriptor);
        this.categoryChanged = categoryChanged;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToEdit = lastShownList.get(index.getZeroBased());
        Book editedBook = createEditedBook(bookToEdit, editBookDescriptor);

        if (!bookToEdit.isSameBook(editedBook) && model.hasBook(editedBook)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        model.setBook(bookToEdit, editedBook);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        if (categoryChanged) {
            return new CommandResult(String.format(MESSAGE_EDIT_CATEGORY_CHANGE, editedBook));
        } else {
            return new CommandResult(String.format(MESSAGE_EDIT_BOOK_SUCCESS, editedBook));
        }
    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToEdit}
     * edited with {@code editBookDescriptor}.
     */
    public static Book createEditedBook(Book bookToEdit, EditBookDescriptor editBookDescriptor) throws CommandException {
        assert bookToEdit != null;
        Title updatedTitle = editBookDescriptor.getTitle().orElse(bookToEdit.getTitle());
        Author updatedAuthor = editBookDescriptor.getAuthor().orElse(bookToEdit.getAuthor());
        Note updatedNote = editBookDescriptor.getNote().orElse(bookToEdit.getNote());
        Category updatedCategory = editBookDescriptor.getCategory().orElse(bookToEdit.getCategory());
        Progress updatedProgress = editBookDescriptor.getProgress().orElse(bookToEdit.getProgress());
        DateStarted updatedDateStarted = editBookDescriptor.getDateStarted().orElse(bookToEdit.getDateStarted());
        DateFinished updatedDateFinished = editBookDescriptor.getDateFinished().orElse(bookToEdit.getDateFinished());
        Rating updatedRating = editBookDescriptor.getRating().orElse(bookToEdit.getRating());
        Set<Tag> updatedTags = editBookDescriptor.getTags().orElse(bookToEdit.getTags());

        // check date started only for read or reading book
        if (updatedCategory.getCategoryValue() == 3 && !editBookDescriptor.getDateStarted().isEmpty()) {
            throw new CommandException(MESSAGE_CONSTRAINTS_FOR_DATESTARTED);
        }

        // check date finished only for read book
        if (updatedCategory.getCategoryValue() != 2 && !editBookDescriptor.getDateFinished().isEmpty()) {
            throw new CommandException(MESSAGE_CONSTRAINTS_FOR_DATEFINISHED);
        }

        // check date finished is after or same day as date started
        if (!updatedDateStarted.isEmpty() && !updatedDateFinished.isEmpty()) {
            if (updatedDateFinished.isBeforeDate(updatedDateStarted)) {
                throw new CommandException(MESSAGE_CONSTRAINTS_FOR_DATE_STARTED_AFTER_FINISHED);
            }
        }

        // check rating only for read book
        if (updatedCategory.getCategoryValue() != 2 && !editBookDescriptor.getRating().isEmpty()) {
            throw new CommandException(MESSAGE_CONSTRAINTS_FOR_RATING);
        }

        return new Book(updatedTitle, updatedAuthor, updatedNote, updatedCategory,
                updatedProgress, bookToEdit.getDateAdded(), updatedDateStarted, updatedDateFinished,
                updatedRating, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editBookDescriptor.equals(e.editBookDescriptor);
    }

    /**
     * Stores the details to edit the book with. Each non-empty field value will replace the
     * corresponding field value of the book.
     */
    public static class EditBookDescriptor {
        private Title title;
        private Progress progress;
        private DateAdded dateAdded;
        private DateStarted dateStarted;
        private DateFinished dateFinished;

        // Data fields
        private Note note;
        private Author author;
        private Category category;
        private Rating rating;
        private Set<Tag> tags;

        public EditBookDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookDescriptor(EditBookDescriptor toCopy) {
            setTitle(toCopy.title);
            setAuthor(toCopy.author);
            setNote(toCopy.note);
            setCategory(toCopy.category);
            setProgress(toCopy.progress);
            setDateAdded(toCopy.dateAdded);
            setDateStarted(toCopy.dateStarted);
            setDateFinished(toCopy.dateFinished);
            setRating(toCopy.rating);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, author, note, category, progress,
                    dateAdded, dateStarted, dateFinished, rating, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Optional<Author> getAuthor() {
            return Optional.ofNullable(author);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setProgress(Progress progress) {
            this.progress = progress;
        }
        public Optional<Progress> getProgress() {
            return Optional.ofNullable(progress);
        }

        public void setDateAdded(DateAdded dateAdded) {
            this.dateAdded = dateAdded;
        }
        public Optional<DateAdded> getDateAdded() {
            return Optional.ofNullable(dateAdded);
        }

        public void setDateStarted(DateStarted dateStarted) {
            this.dateStarted = dateStarted;
        }
        public Optional<DateStarted> getDateStarted() {
            return Optional.ofNullable(dateStarted);
        }

        public void setDateFinished(DateFinished dateFinished) {
            this.dateFinished = dateFinished;
        }
        public Optional<DateFinished> getDateFinished() {
            return Optional.ofNullable(dateFinished);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }
        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBookDescriptor)) {
                return false;
            }

            // state check
            EditBookDescriptor e = (EditBookDescriptor) other;

            return e.getTitle().equals(getTitle())
                    && e.getAuthor().equals(getAuthor())
                    && e.getNote().equals(getNote())
                    && e.getCategory().equals(getCategory())
                    && e.getProgress().equals(getProgress())
                    && e.getDateAdded().equals(getDateAdded())
                    && e.getDateStarted().equals(getDateStarted())
                    && e.getDateFinished().equals(getDateFinished())
                    && e.getRating().equals(getRating())
                    && e.getTags().equals(getTags());
        }


    }
}
