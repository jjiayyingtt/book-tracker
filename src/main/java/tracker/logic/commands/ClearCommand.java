package tracker.logic.commands;

import static java.util.Objects.requireNonNull;

import tracker.model.BookTracker;
import tracker.model.Model;

/**
 * Clears the address book by setting a new Address Book
 * Possible enhancement: confirmation of clear
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Book tracker has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBookTracker(new BookTracker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
