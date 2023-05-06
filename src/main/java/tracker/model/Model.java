package tracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tracker.commons.core.GuiSettings;
import tracker.model.book.Book;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' book tracker file path.
     */
    Path getBookTrackerFilePath();

    /**
     * Sets the user prefs' book tracker file path.
     */
    void setBookTrackerFilePath(Path bookTrackerFilePath);

    /**
     * Replaces book tracker data with the data in {@code bookTracker}.
     */
    void setBookTracker(ReadOnlyBookTracker bookTracker);

    /** Returns the BookTracker */
    ReadOnlyBookTracker getBookTracker();

    /**
     * Returns true if a book with the same identity as {@code book} exists in the book tracker.
     */
    boolean hasBook(Book book);

    /**
     * Deletes the given book.
     * The book must exist in the book tracker.
     */
    void deleteBook(Book target);

    /**
     * Adds the given book.
     * {@code book} must not already exist in the book tracker.
     */
    void addBook(Book book);
    /**
     * Return the size of book tracker.
     */
    int size();

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     * {@code target} must exist in the book tracker.
     * The book identity of {@code editedBook} must not be the same as another existing book in the book tracker.
     */
    void setBook(Book target, Book editedBook);

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    Book getCurrentlyReading();



    /** Returns an unmodifiable view of the book list, sorted by date added */
    void sortBookList(String type, boolean ascending);

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

    /**** Usr Goal API ****/
    Path getUserGoalFilePath();
    void setUserGoalFilePath(Path userGoalFilePath);
    UserGoal getUserGoal();
    void setGoal(String goal);
    String getGoal();
    void setCurrent(String current);
    String getCurrent();
}
