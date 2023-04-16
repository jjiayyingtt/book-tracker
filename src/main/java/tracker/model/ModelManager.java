package tracker.model;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tracker.commons.core.GuiSettings;
import tracker.commons.core.LogsCenter;
import tracker.model.book.Book;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BookTracker addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Book> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyBookTracker addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new BookTracker(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getBookList());
    }

    public ModelManager() {
        this(new BookTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getBookTrackerFilePath() {
        return userPrefs.getBookTrackerFilePath();
    }

    @Override
    public void setBookTrackerFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setBookTrackerFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setBookTracker(ReadOnlyBookTracker addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyBookTracker getBookTracker() {
        return addressBook;
    }

    @Override
    public boolean hasBook(Book person) {
        requireNonNull(person);
        return addressBook.hasBook(person);
    }

    @Override
    public void deleteBook(Book target) {
        addressBook.removeBook(target);
    }

    @Override
    public void addBook(Book person) {
        addressBook.addBook(person);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public void setBook(Book target, Book editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setBook(target, editedPerson);
    }

    @Override
    public int size() {
        System.out.println(addressBook.size());
        return addressBook.size();
    }

    public Book getCurrentlyReading() {
        return addressBook.getCurrentlyReading();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return filteredPersons;
    }

    @Override
    public void sortBookList(String type, boolean ascending) {
        switch (type) {
            case "title":
                addressBook.sortBooksTitle(ascending);
                break;
            case "author":
                addressBook.sortBooksCategory(ascending); // add sortBooksAuthor
                break;
            case "date":
                addressBook.sortBooksCategory(ascending); // add sortBooksDate
                break;
            default:
                addressBook.sortBooksTitle(ascending);
        }

        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
