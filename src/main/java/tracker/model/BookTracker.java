package tracker.model;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;

import javafx.collections.FXCollections;
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
     * Creates an Book Tracker using the Books in the {@code toBeCopied}
     */
    public BookTracker(ReadOnlyBookTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the book list with {@code books}.
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
        updateCurrentlyReading();
        return currentlyReading;
    }

    public ObservableList<Book> filterCurrentlyReading() {
        ObservableList<Book> filteredOldList = FXCollections.observableArrayList();
        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            Book temp = bookIterator.next();
            if (temp.getCategory().getCategoryValue() == 1) {
                filteredOldList.add(temp);
            }
        }
        return filteredOldList;
    }

    public void updateCurrentlyReading() {
        ObservableList<Book> sortedOldList = filterCurrentlyReading();
        sortedOldList = sortedOldList.sorted(Comparator.comparing(Book::getDateAdded).reversed());
        if (!sortedOldList.isEmpty()) {
            this.currentlyReading = sortedOldList.get(0);
        } else {
            this.currentlyReading = null;
        }
    }

    public String getCurrentBooksRead() {
        int currentBooksRead = 0;
        int year = Year.now().getValue();
        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            Book temp = bookIterator.next();
            if (temp.getDateFinished().value.equals("-")){
                continue;
            }
            try {
                if (temp.getCategory().getCategoryValue() == 2 &&
                        new SimpleDateFormat("yyyy/MM/dd").parse(temp.getDateFinished().value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()
                                == year) {
                    currentBooksRead++;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return String.valueOf(currentBooksRead);
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

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing DateAdded
     */
    public void sortBooksDateAdded(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getDateAdded));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getDateAdded).reversed());
        }
        this.setBooks(sortedOldList);
    }


    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing DateStarted
     */
    public void sortBooksDateStarted(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getDateStarted));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getDateStarted).reversed());
        }
        this.setBooks(sortedOldList);
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing DateFinished
     */
    public void sortBooksDateFinished(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getDateFinished));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getDateFinished).reversed());
        }
        this.setBooks(sortedOldList);
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing Rating
     */
    public void sortBooksRating(boolean ascending) {
        List<Book> sortedOldList;
        if (ascending) {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getRating));
        } else {
            sortedOldList = this.getBookList()
                    .sorted(Comparator.comparing(Book::getRating).reversed());
        }
        this.setBooks(sortedOldList);
    }

    //// book-level operations

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
