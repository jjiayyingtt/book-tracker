package tracker.logic.parser;

import static java.util.Objects.requireNonNull;
import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tracker.logic.parser.CliSyntax.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import tracker.commons.core.index.Index;
import tracker.logic.commands.EditCommand;
import tracker.logic.commands.EditCommand.EditBookDescriptor;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AUTHOR, PREFIX_NOTE, PREFIX_CATEGORY,
                        PREFIX_PROGRESS, PREFIX_DATESTARTED, PREFIX_DATEFINISHED, PREFIX_RATING, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditBookDescriptor editBookDescriptor = new EditBookDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editBookDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            editBookDescriptor.setAuthor(ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editBookDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editBookDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        if (argMultimap.getValue(PREFIX_PROGRESS).isPresent()) {
            editBookDescriptor.setProgress(ParserUtil.parseProgress(
                    argMultimap.getValue(PREFIX_PROGRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_DATESTARTED).isPresent()) {
            editBookDescriptor.setDateStarted(ParserUtil.parseDateStarted(
                    argMultimap.getValue(PREFIX_DATESTARTED)));
        }
        if (argMultimap.getValue(PREFIX_DATEFINISHED).isPresent()) {
            editBookDescriptor.setDateFinished(ParserUtil.parseDateFinished(
                    argMultimap.getValue(PREFIX_DATEFINISHED)));
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            editBookDescriptor.setRating(ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING)));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editBookDescriptor::setTags);

        if (!editBookDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }


        return new EditCommand(index, editBookDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
