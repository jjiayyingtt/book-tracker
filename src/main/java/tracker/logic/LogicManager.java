package tracker.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tracker.commons.core.GuiSettings;
import tracker.commons.core.LogsCenter;
import tracker.logic.commands.Command;
import tracker.logic.commands.CommandResult;
import tracker.logic.commands.exceptions.CommandException;
import tracker.logic.parser.BookTrackerParser;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.Model;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.UserGoal;
import tracker.model.book.Book;
import tracker.storage.TrackerStorage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final TrackerStorage storage;
    private final BookTrackerParser bookTrackerParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code TrackerStorage}.
     */
    public LogicManager(Model model, TrackerStorage storage) {
        this.model = model;
        this.storage = storage;
        bookTrackerParser = new BookTrackerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;
        Command command = bookTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveBookTracker(model.getBookTracker());
            storage.saveUserGoal(model.getUserGoal());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBookTracker getBookTracker() {
        return model.getBookTracker();
    }

    @Override
    public ObservableList<Book> getFilteredPersonList() {
        return model.getFilteredBookList();
    }

    @Override
    public Book getCurrentlyReading() {
        return model.getCurrentlyReading();
    }

    @Override
    public UserGoal getUserGoal() {
        return model.getUserGoal();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getBookTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
