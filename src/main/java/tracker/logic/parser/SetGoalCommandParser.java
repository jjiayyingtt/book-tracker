package tracker.logic.parser;

import tracker.logic.commands.SetGoalCommand;
import tracker.logic.commands.SortCommand;
import tracker.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.Arrays;

import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SetGoalCommandParser {


    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetGoalCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetGoalCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (nameKeywords.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetGoalCommand.MESSAGE_USAGE));
        }
        int goal = Integer.parseInt(nameKeywords[0]);

        if (goal < 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetGoalCommand.MESSAGE_USAGE));
        }


        return new SetGoalCommand(String.valueOf(goal));
    }
}
