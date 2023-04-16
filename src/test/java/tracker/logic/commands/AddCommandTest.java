package tracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tracker.commons.core.GuiSettings;
import tracker.commons.core.index.Index;
import tracker.logic.commands.exceptions.CommandException;
import tracker.model.BookTracker;
import tracker.model.Model;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.ReadOnlyUserPrefs;
import tracker.model.book.Book;
import tracker.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Book validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Book validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_BOOK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Book alice = new PersonBuilder().withName("Alice").build();
        Book bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBookTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookTrackerFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBook(Book person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookTracker(ReadOnlyBookTracker newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBookTracker getBookTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(Book person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBook(Book target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBook(Book target, Book editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int size() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns an unmodifiable view of the person list, sorted by business size
         *
         * @param type
         * @param ascending
         */
        @Override
        public void sortBookList(String type, boolean ascending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markPerson(Index index, Mark mark) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Book person;

        ModelStubWithPerson(Book person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasBook(Book person) {
            requireNonNull(person);
            return this.person.isSameBook(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Book> personsAdded = new ArrayList<>();

        @Override
        public boolean hasBook(Book person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSameBook);
        }

        @Override
        public void addBook(Book person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyBookTracker getBookTracker() {
            return new BookTracker();
        }
    }

}
