package tracker.testutil;

import static tracker.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static tracker.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static tracker.logic.parser.CliSyntax.PREFIX_DATEADDED;
import static tracker.logic.parser.CliSyntax.PREFIX_NOTE;
import static tracker.logic.parser.CliSyntax.PREFIX_TITLE;
import static tracker.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static tracker.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import tracker.logic.commands.AddCommand;
import tracker.logic.commands.EditCommand.EditBookDescriptor;
import tracker.model.book.Book;
import tracker.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code book}.
     */
    public static String getAddCommand(Book person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code book}'s details.
     */
    public static String getPersonDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + book.getTitle().fullTitle + " ");
        sb.append(PREFIX_AUTHOR + book.getAuthor().value + " ");
        sb.append(PREFIX_NOTE + book.getNote().value + " ");
        sb.append(PREFIX_CATEGORY + book.getCategory().value + " ");
        sb.append(PREFIX_PROGRESS + book.getProgress().value + " ");
        sb.append(PREFIX_DATEADDED + book.getDateAdded().value + " ");
        book.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditBookDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_TITLE).append(name.fullTitle).append(" "));
        descriptor.getAuthor().ifPresent(author -> sb.append(PREFIX_AUTHOR).append(author.value).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note.value).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY).append(category.value).append(" "));
        descriptor.getProgress().ifPresent(progress -> sb.append(PREFIX_PROGRESS).append(progress.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
