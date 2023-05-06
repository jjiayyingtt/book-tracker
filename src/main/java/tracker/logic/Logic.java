package tracker.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import tracker.commons.core.GuiSettings;
import tracker.logic.commands.CommandResult;
import tracker.logic.commands.exceptions.CommandException;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.book.Book;
import tracker.model.UserGoal;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see tracker.model.Model#getBookTracker()
     */
    ReadOnlyBookTracker getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Book> getFilteredPersonList();

    Book getCurrentlyReading();

    UserGoal getUserGoal();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
