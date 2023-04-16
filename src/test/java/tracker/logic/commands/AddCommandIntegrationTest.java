package tracker.logic.commands;

import static tracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tracker.model.Model;
import tracker.model.ModelManager;
import tracker.model.UserPrefs;
import tracker.model.book.Book;
import tracker.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Book validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getBookTracker(), new UserPrefs());
        expectedModel.addBook(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Book personInList = model.getBookTracker().getBookList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_BOOK);
    }

}
