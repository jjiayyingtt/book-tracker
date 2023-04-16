package tracker.logic.commands;

import static tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tracker.logic.commands.SummaryCommand.SHOWING_SUMMARY_MESSAGE;

import org.junit.jupiter.api.Test;

import tracker.model.Model;
import tracker.model.ModelManager;

public class SummaryCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_summary_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_SUMMARY_MESSAGE, false, true, false);
        assertCommandSuccess(new SummaryCommand(), model, expectedCommandResult, expectedModel);
    }
}
