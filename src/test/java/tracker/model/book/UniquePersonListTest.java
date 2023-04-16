package tracker.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tracker.testutil.Assert.assertThrows;
import static tracker.testutil.TypicalPersons.ALICE;
import static tracker.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tracker.model.book.exceptions.DuplicateBookException;
import tracker.model.book.exceptions.BookNotFoundException;
import tracker.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniqueBookList uniqueBookList = new UniqueBookList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueBookList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueBookList.add(ALICE);
        assertTrue(uniqueBookList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookList.add(ALICE);
        Book editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueBookList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueBookList.add(ALICE);
        assertThrows(DuplicateBookException.class, () -> uniqueBookList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBook(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBook(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(BookNotFoundException.class, () -> uniqueBookList.setBook(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueBookList.add(ALICE);
        uniqueBookList.setBook(ALICE, ALICE);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(ALICE);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueBookList.add(ALICE);
        Book editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueBookList.setBook(ALICE, editedAlice);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(editedAlice);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueBookList.add(ALICE);
        uniqueBookList.setBook(ALICE, BOB);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueBookList.add(ALICE);
        uniqueBookList.add(BOB);
        assertThrows(DuplicateBookException.class, () -> uniqueBookList.setBook(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(BookNotFoundException.class, () -> uniqueBookList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueBookList.add(ALICE);
        uniqueBookList.remove(ALICE);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBooks((UniqueBookList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueBookList.add(ALICE);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        uniqueBookList.setBooks(expectedUniqueBookList);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBooks((List<Book>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueBookList.add(ALICE);
        List<Book> personList = Collections.singletonList(BOB);
        uniqueBookList.setBooks(personList);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Book> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateBookException.class, () -> uniqueBookList.setBooks(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBookList.asUnmodifiableObservableList().remove(0));
    }
}
