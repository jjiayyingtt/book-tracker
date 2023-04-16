package tracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tracker.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static tracker.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static tracker.logic.parser.CliSyntax.PREFIX_NOTE;
import static tracker.logic.parser.CliSyntax.PREFIX_TITLE;
import static tracker.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static tracker.logic.parser.CliSyntax.PREFIX_TAG;
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
import tracker.model.Model;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * Edits the details of an existing book in the book tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_PROGRESS + "PROGRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AUTHOR + "Mark Haddon "
            + PREFIX_NOTE + "not so great";

    public static final String MESSAGE_EDIT_BOOK_SUCCESS = "Edited Book: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the address book.";

    private final Index index;
    private final EditBookDescriptor editBookDescriptor;

    /**
     * @param index of the book in the filtered book list to edit
     * @param editBookDescriptor details to edit the book with
     */
    public EditCommand(Index index, EditBookDescriptor editBookDescriptor) {
        requireNonNull(index);
        requireNonNull(editBookDescriptor);

        this.index = index;
        this.editBookDescriptor = new EditBookDescriptor(editBookDescriptor);
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
        return new CommandResult(String.format(MESSAGE_EDIT_BOOK_SUCCESS, editedBook));
    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToEdit}
     * edited with {@code editBookDescriptor}.
     */
    public static Book createEditedBook(Book bookToEdit, EditBookDescriptor editBookDescriptor) {
        assert bookToEdit != null;
        Title updatedTitle = editBookDescriptor.getTitle().orElse(bookToEdit.getTitle());
        Author updatedAuthor = editBookDescriptor.getAuthor().orElse(bookToEdit.getAuthor());
        Note updatedNote = editBookDescriptor.getNote().orElse(bookToEdit.getNote());
        Category updatedCategory = editBookDescriptor.getCategory().orElse(bookToEdit.getCategory());
        Progress updatedProgress = editBookDescriptor.getProgress().orElse(bookToEdit.getProgress());
        Set<Tag> updatedTags = editBookDescriptor.getTags().orElse(bookToEdit.getTags());

        return new Book(updatedTitle, updatedAuthor, updatedNote, updatedCategory,
                updatedProgress, bookToEdit.getDateAdded(), updatedTags);
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
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditBookDescriptor {
        private Title title;
        private Progress progress;
        private DateAdded dateAdded;

        // Data fields
        private Note note;
        private Author author;
        private Category category;
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
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, author, note, category, progress,
                    dateAdded, tags);
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
                    && e.getTags().equals(getTags());
        }


    }
}
