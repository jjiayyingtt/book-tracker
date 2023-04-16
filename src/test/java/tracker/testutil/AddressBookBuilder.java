package tracker.testutil;

import tracker.model.BookTracker;
import tracker.model.book.Book;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private BookTracker addressBook;

    public AddressBookBuilder() {
        addressBook = new BookTracker();
    }

    public AddressBookBuilder(BookTracker addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Book person) {
        addressBook.addBook(person);
        return this;
    }

    public BookTracker build() {
        return addressBook;
    }
}
