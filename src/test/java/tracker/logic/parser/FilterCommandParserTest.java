package tracker.logic.parser;

import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tracker.logic.commands.FilterCommand;
import tracker.model.book.BookContainsTagsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new BookContainsTagsPredicate(Arrays.asList("life", "car")));
        // new FilterCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "life car", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n life \n \t car  \t", expectedFilterCommand);
    }

}
