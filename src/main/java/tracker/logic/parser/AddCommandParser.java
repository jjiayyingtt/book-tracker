package tracker.logic.parser;

import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tracker.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import tracker.logic.commands.AddCommand;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.book.Note;
import tracker.model.book.Author;
import tracker.model.book.DateAdded;
import tracker.model.book.Title;
import tracker.model.book.Book;
import tracker.model.book.Progress;
import tracker.model.book.Category;
import tracker.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AUTHOR, PREFIX_NOTE, PREFIX_CATEGORY,
                        PREFIX_PROGRESS, PREFIX_DATEADDED, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_AUTHOR, PREFIX_NOTE, PREFIX_CATEGORY,
                PREFIX_PROGRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Author author = ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Progress progress = ParserUtil.parseProgress(argMultimap.getValue(PREFIX_PROGRESS).get());
        DateAdded dateAdded = ParserUtil.parseDateAdded();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Book book = new Book(title, author, note, category, progress, dateAdded, tagList);

        return new AddCommand(book);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
