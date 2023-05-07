package tracker.logic.parser;

import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tracker.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import tracker.logic.commands.AddCommand;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String MESSAGE_CONSTRAINTS_FOR_DATESTARTED=
            "Only read or currently reading books can have date started.";
    public static final String MESSAGE_CONSTRAINTS_FOR_DATEFINISHED =
            "Only read books can have date finished.";

    public static final String MESSAGE_CONSTRAINTS_FOR_DATE_STARTED_AFTER_FINISHED =
            "Date started cannot be after date finished.";
    public static final String MESSAGE_CONSTRAINTS_FOR_RATING =
            "Only read books can have rating.";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AUTHOR, PREFIX_NOTE, PREFIX_CATEGORY,
                        PREFIX_PROGRESS, PREFIX_DATEADDED, PREFIX_DATESTARTED, PREFIX_DATEFINISHED,
                        PREFIX_RATING, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_AUTHOR, PREFIX_NOTE, PREFIX_CATEGORY,
                PREFIX_PROGRESS) // make field optional here
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Author author = ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Progress progress = ParserUtil.parseProgress(argMultimap.getValue(PREFIX_PROGRESS).get());
        DateAdded dateAdded = ParserUtil.parseDateAdded();
        DateStarted dateStarted = ParserUtil.parseDateStarted(argMultimap.getValue(PREFIX_DATESTARTED));
        DateFinished dateFinished = ParserUtil.parseDateFinished(argMultimap.getValue(PREFIX_DATEFINISHED));
        Rating rating = ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // check date started only for read or reading book
        if (category.getCategoryValue() == 3 && !argMultimap.getValue(PREFIX_DATESTARTED).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_CONSTRAINTS_FOR_DATESTARTED, AddCommand.MESSAGE_USAGE));
        }

        // check date finished only for read book
        if (category.getCategoryValue() != 2 && !dateFinished.toString().equals("-")) {
            throw new ParseException(String.format(MESSAGE_CONSTRAINTS_FOR_DATEFINISHED, AddCommand.MESSAGE_USAGE));
        }

        // check date finished is after or same day as date started
        if (!argMultimap.getValue(PREFIX_DATESTARTED).isEmpty() && !argMultimap.getValue(PREFIX_DATEFINISHED).isEmpty()) {
            if (dateFinished.isBeforeDate(dateStarted)) {
                throw new ParseException(String.format(MESSAGE_CONSTRAINTS_FOR_DATE_STARTED_AFTER_FINISHED, AddCommand.MESSAGE_USAGE));
            }
        }


        // check rating only for read book
        if (category.getCategoryValue() != 2 && !argMultimap.getValue(PREFIX_RATING).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_CONSTRAINTS_FOR_RATING, AddCommand.MESSAGE_USAGE));
        }

        Book book = new Book(title, author, note, category, progress, dateAdded, dateStarted, dateFinished,
                rating, tagList);

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
