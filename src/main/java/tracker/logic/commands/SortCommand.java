package tracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tracker.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import tracker.model.Model;

/**
 * Sort the book tracker whose name by a given field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all books according to a specified field "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [title, author, date] [asc, desc]...\n"
            + "Example: " + COMMAND_WORD + " date asc \n"
            + "Note that only 2 arguments may be specified at one time. "
            + "One specifying the field, the other, direction.";

    public static final String MESSAGE_SUCCESS = "Sorted by: ";
    private final String subCommand;
    private final String direction;
    private final boolean ascending;

    /**
     * Creates an SortCommand to sort the list {@code Book}
     */
    public SortCommand(String subCommand, String direction) {
        //TODO assert subcommand and direction is correct
        this.subCommand = subCommand.toLowerCase();
        this.direction = direction;
        this.ascending = direction.equalsIgnoreCase("asc");
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortBookList(subCommand, ascending);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(MESSAGE_SUCCESS + this.subCommand + " " + this.direction);
    }
}
