package tracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tracker.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import tracker.model.Model;
import tracker.model.book.BookInCategoryPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    private final String category;

    private final BookInCategoryPredicate predicate;

    public static final String MESSAGE_SUCCESS = "Listed all books";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List books in certain category "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [all, want, reading, read]...\n"
            + "Example: " + COMMAND_WORD + " all \n";

    public ListCommand(String category) {
        this.category = category.toLowerCase();
        this.predicate = new BookInCategoryPredicate(category);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (category.equals("all")) {
            model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        } else {
            model.updateFilteredBookList(predicate);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
