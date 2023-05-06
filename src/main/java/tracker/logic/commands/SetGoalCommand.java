package tracker.logic.commands;

import tracker.logic.commands.exceptions.CommandException;
import tracker.model.Model;
import tracker.model.UserGoal;

import static java.util.Objects.requireNonNull;

public class SetGoalCommand extends Command {

    public static final String COMMAND_WORD = "setgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set your reading goal.\n"
            + "Parameters: "
            + "UserGoal (cannot be negative)";

    public static final String MESSAGE_SUCCESS = "Your goal has been set";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book tracker";
    private String goalToSet;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SetGoalCommand(String goal) {
        requireNonNull(goal);
        goalToSet = goal;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setGoal(goalToSet);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
