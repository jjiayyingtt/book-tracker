package tracker.logic.parser;

import tracker.logic.commands.ListCommand;
import tracker.logic.commands.SortCommand;
import tracker.logic.parser.exceptions.ParseException;
import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCommandParser implements Parser<ListCommand> {

    public static final String MESSAGE_CONSTRAINTS =
            "Only read or currently reading books can have date started.";

    private final ArrayList<String> commandString =
            new ArrayList<>(Arrays.asList("all", "want", "reading", "read"));

    @Override
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (nameKeywords.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        String categoryKeyword = nameKeywords[0].toLowerCase();
        if (!commandString.contains(categoryKeyword)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand(categoryKeyword);
    }
}
