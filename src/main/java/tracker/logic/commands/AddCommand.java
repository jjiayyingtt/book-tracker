package tracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tracker.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static tracker.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static tracker.logic.parser.CliSyntax.PREFIX_NOTE;
import static tracker.logic.parser.CliSyntax.PREFIX_TITLE;
import static tracker.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static tracker.logic.parser.CliSyntax.PREFIX_RATING;
import static tracker.logic.parser.CliSyntax.PREFIX_TAG;

import tracker.logic.commands.exceptions.CommandException;
import tracker.model.Model;
import tracker.model.book.Book;

/**
 * Adds a book to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a book to the book tracker.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AUTHOR + "AUTHOR "
            + PREFIX_NOTE + "NOTE "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_PROGRESS + "PROGRESS "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "The Curious Incident of the Dog in the Night-Time "
            + PREFIX_AUTHOR + "Mark Haddon "
            + PREFIX_NOTE + "so good so far "
            + PREFIX_CATEGORY + "READING "
            + PREFIX_PROGRESS + "76 "
            + PREFIX_TAG + "Fiction "
            + PREFIX_TAG + "Mystery";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book tracker";

    private final Book toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBook(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        model.addBook(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
