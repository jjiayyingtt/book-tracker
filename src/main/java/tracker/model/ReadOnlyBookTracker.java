package tracker.model;

import javafx.collections.ObservableList;
import tracker.model.book.Book;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyBookTracker {

    /**
     * Returns an unmodifiable view of the book list.
     * This list will not contain any duplicate book.
     */
    ObservableList<Book> getBookList();

    /**
     * Returns size of the book list.
     */
    int size();

    /**
     * Returns the a string containing all tags.
     */
    String getTags();


}
