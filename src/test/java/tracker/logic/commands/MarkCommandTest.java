package tracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tracker.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tracker.model.Model;
import tracker.model.ModelManager;
import tracker.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCommand}.
 */
public class MarkCommandTest {

    @Test
    public void executeValidIndexUnfilteredListMarkSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, new Mark("YES"));

        String expectedMessage = MarkCommand.MESSAGE_MARK_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getBookTracker(), new UserPrefs());
        expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("no"));
        expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("YES"));

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexUnfilteredListUnmarkSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ModelManager expectedModel = new ModelManager(model.getBookTracker(), new UserPrefs());

        expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("YES"));
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, new Mark("no"));

        String expectedMessage = MarkCommand.MESSAGE_UNMARK_SUCCESS;
        expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("no"));

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexUnfilteredListMarkFailureAlrMark() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, new Mark("YES"));

        String expectedMessage = MarkCommand.MESSAGE_PERSON_ALR_MARKED;

        ModelManager expectedModel = new ModelManager(model.getBookTracker(), new UserPrefs());
        expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("YES"));
        //expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("YES"));

        assertCommandFailure(markCommand, model, expectedMessage);
    }

    @Test
    public void executeValidIndexUnfilteredListMarkFailureAlrUNmark() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, new Mark("No"));

        String expectedMessage = MarkCommand.MESSAGE_PERSON_ALR_UNMARKED;

        ModelManager expectedModel = new ModelManager(model.getBookTracker(), new UserPrefs());
        expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("no"));
        //expectedModel.markPerson(INDEX_FIRST_PERSON, new Mark("no"));

        assertCommandFailure(markCommand, model, expectedMessage);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredBookList(p -> false);

        assertTrue(model.getFilteredBookList().isEmpty());
    }
}
