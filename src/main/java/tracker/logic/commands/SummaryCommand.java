package tracker.logic.commands;

import java.lang.reflect.InvocationTargetException;

import tracker.logic.commands.exceptions.CommandException;
import tracker.model.Model;
import tracker.ui.SummaryWindow;

/**
 * Format full summary view for display.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SUMMARY_MESSAGE = "Opened summary window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_SUMMARY_MESSAGE, false, true, false);
    }
}
