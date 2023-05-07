package tracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import tracker.commons.core.index.Index;
import tracker.commons.util.StringUtil;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    // TODO: 9/4/2023 change variable names
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String author} into a {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code author} is invalid.
     */
    public static Author parseAuthor(String author) throws ParseException {
        requireNonNull(author);
        String trimmedAuthor = author.trim();
        if (!Author.isValidAuthor(trimmedAuthor)) {
            throw new ParseException(Author.MESSAGE_CONSTRAINTS);
        }
        return new Author(trimmedAuthor);
    }

    /**
     * Parses a {@code String note} into an {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses a {@code String category} into an {@code Category}.
     *
     * @throws ParseException if the given {@code Category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategory(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses a {@code String progress} into an {@code Progress}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code progress} is invalid.
     */
    public static Progress parseProgress(String progress) throws ParseException {
        requireNonNull(progress);
        String trimmedProgress = progress.trim();
        if (!Progress.isValidProgress(trimmedProgress)) {
            throw new ParseException(Progress.MESSAGE_CONSTRAINTS);
        }
        return new Progress(trimmedProgress);
    }


    /**
     * Parses a {@code String AddedDate} into an {@code AddedDate}.
     *
     * @throws ParseException if the given {@code AddedDate} is invalid.
     */
    public static DateAdded parseDateAdded() throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return new DateAdded(dtf.format(now));
    }

    /**
     * Parses a {@code String DateStarted} into an {@code DateStarted}.
     *
     * @throws ParseException if the given {@code DateStarted} is invalid.
     */
    public static DateStarted parseDateStarted(Optional<String> dateStarted) throws ParseException {
        return dateStarted.isEmpty() ? new DateStarted("-") : new DateStarted(dateStarted.get());
    }

    /**
     * Parses a {@code String dateFinished} into an {@code dateFinished}.
     *
     * @throws ParseException if the given {@code dateFinished} is invalid.
     */
    public static DateFinished parseDateFinished(Optional<String> dateFinished) throws ParseException {
        return dateFinished.isEmpty() ? new DateFinished("-") : new DateFinished(dateFinished.get());
    }

    /**
     * Parses a {@code String rating} into an {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Rating parseRating(Optional<String> rating) throws ParseException {
        if (rating.isEmpty()) {
            return new Rating("0");
        } else {
            String trimmedRating = rating.get().trim();
            if (!Rating.isValidRating(trimmedRating)) {
                throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
            }
            return new Rating(trimmedRating);
        }

    }



    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }


}
