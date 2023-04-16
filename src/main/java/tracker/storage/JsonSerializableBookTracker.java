package tracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tracker.commons.exceptions.IllegalValueException;
import tracker.model.BookTracker;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.book.Book;

/**
 * An Immutable BookTracker that is serializable to JSON format.
 */
@JsonRootName(value = "bookTracker")
class JsonSerializableBookTracker {

    public static final String MESSAGE_DUPLICATE_BOOK = "Book list contains duplicate book(s).";

    private final List<JsonAdaptedBook> books = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBookTracker} with the given books.
     */
    @JsonCreator
    public JsonSerializableBookTracker(@JsonProperty("books") List<JsonAdaptedBook> books) {
        this.books.addAll(books);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBookTracker}.
     */
    public JsonSerializableBookTracker(ReadOnlyBookTracker source) {
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this book tracker into the model's {@code BookTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BookTracker toModelType() throws IllegalValueException {
        BookTracker bookTracker = new BookTracker();
        for (JsonAdaptedBook jsonAdaptedBook : books) {
            Book book = jsonAdaptedBook.toModelType();
            if (bookTracker.hasBook(book)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOK);
            }
            bookTracker.addBook(book);
        }
        return bookTracker;
    }

}
