package tracker.logic.commands;

import static tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tracker.model.BookTracker;
import tracker.model.Model;
import tracker.model.ModelManager;
import tracker.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setBookTracker(new BookTracker());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
