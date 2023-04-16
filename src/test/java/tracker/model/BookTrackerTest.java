package tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tracker.testutil.Assert.assertThrows;
import static tracker.testutil.TypicalPersons.ALICE;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.*;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tracker.model.book.Author;
import tracker.model.book.Book;
import tracker.model.book.exceptions.DuplicateBookException;
import tracker.model.tag.Tag;
import tracker.testutil.PersonBuilder;

public class BookTrackerTest {

    private final BookTracker addressBook = new BookTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getBookList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        BookTracker newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Book editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Book> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicateBookException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasBook(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasBook(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addBook(ALICE);
        assertTrue(addressBook.hasBook(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addBook(ALICE);
        Book editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasBook(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getBookList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyBookTracker {
        private final ObservableList<Book> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Book> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Book> getBookList() {
            return persons;
        }


        @Override
        public int size() {
            return persons.size();
        }

        @Override
        public String getTags() {
            Iterator<Book> personIterator = persons.iterator();
            Set<Tag> tags = new HashSet<>();
            String tagsInString = "";
            while (personIterator.hasNext()) {
                tags.addAll(personIterator.next().getTags());
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

    }

}
