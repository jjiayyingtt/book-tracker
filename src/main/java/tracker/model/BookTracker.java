package tracker.model;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import javafx.collections.ObservableList;
import tracker.model.book.Book;
import tracker.model.book.UniqueBookList;
import tracker.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class BookTracker implements ReadOnlyBookTracker {

    private final UniqueBookList books;
    private Book currentlyReading;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        books = new UniqueBookList();
    }

    public BookTracker() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public BookTracker(ReadOnlyBookTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code books}.
     * {@code books} must not contain duplicate books.
     */
    public void setBooks(List<Book> books) {
        this.books.setBooks(books);
    }

    /**
     * Resets the existing data of this {@code BookTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyBookTracker newData) {
        requireNonNull(newData);
        setBooks(newData.getBookList());
    }

    public Book getCurrentlyReading() {
        List<Book> sortedOldList = filterCurrentlyReading();
        sortedOldList = this.getBookList()
                .sorted(Comparator.comparing(Book::getDateAdded).reversed());
        currentlyReading = sortedOldList.get(0);
        return currentlyReading;
    }

    public List<Book> filterCurrentlyReading() {
        List<Book> filteredOldList = new ArrayList<>();
        // naive todo make it not naive.
        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            Book temp = bookIterator.next();
            if (temp.getCategory().getCategoryValue() == 1) {
                filteredOldList.add(temp);
            }
        }
        return filteredOldList;
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing Category
     * ascending or descending depends on input
     */
    public void sortBooksCategory(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getCategoryInt));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getCategoryInt).reversed());
        }
        this.setBooks(sortedOldList);
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing Title in alphabetical order
     */
    public void sortBooksTitle(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getStandardisedNameString));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getStandardisedNameString).reversed());
        }
        this.setBooks(sortedOldList);
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing Author in alphabetical order
     */
    public void sortBooksAuthor(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getStandardisedAuthorString));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getStandardisedAuthorString).reversed());
        }
        this.setBooks(sortedOldList);
    }

    //// person-level operations

    /**
     * Returns true if a book with the same identity as {@code book} exists in the address book.
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return books.contains(book);
    }

    /**
     * Adds a book to the book tracker.
     * The book must not already exist in the book tracker.
     */
    public void addBook(Book book) {
        books.add(book);
        //getCurrentlyReading();
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the book tracker.
     * The book identity of {@code editedBook} must not be the same as another existing book in the book tracker.
     */
    public void setBook(Book target, Book editedBook) {
        requireNonNull(editedBook);
        books.setBook(target, editedBook);
    }

    /**
     * Removes {@code key} from this {@code BookTracker}.
     * {@code key} must exist in the book tracker.
     */
    public void removeBook(Book key) {
        books.remove(key);
    }

    /**
     * Return the size of the list.
     *
     */
    @Override
    public int size() {
        return books.size();
    }

    /**
     * Returns the a string containing all tags.
     */
    @Override
    public String getTags() {
        Iterator<Book> bookIterator = books.iterator();
        Set<Tag> tags = new HashSet<>();
        String tagsInString = "";
        while (bookIterator.hasNext()) {
            tags.addAll(bookIterator.next().getTags());
        }
        Iterator<Tag> tagIterator = tags.iterator();
        while (tagIterator.hasNext()) {
            Tag temp = tagIterator.next();
            if (tagIterator.hasNext()) {
                tagsInString += temp + ", ";
            } else {
                tagsInString += temp;
            }
        }
        return tagsInString;
    }

    //// util methods

    @Override
    public String toString() {
        return books.asUnmodifiableObservableList().size() + " books";
        // TODO: refine later
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookTracker // instanceof handles nulls
                && books.equals(((BookTracker) other).books));
    }

    @Override
    public int hashCode() {
        return books.hashCode();
    }
}
