package tracker.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tracker.commons.core.GuiSettings;
import tracker.commons.core.LogsCenter;
import tracker.logic.Logic;
import tracker.logic.commands.CommandResult;
import tracker.logic.commands.exceptions.CommandException;
import tracker.logic.parser.exceptions.ParseException;
import tracker.model.UserGoal;
import tracker.model.book.Book;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BookListPanel bookListPanel;
    private ResultDisplay resultDisplay;
    private CurrentlyReadingDisplay currentlyReadingDisplay;
    private UserGoalDisplay userGoalDisplay;

    private HelpWindow helpWindow;

    private AddBookWindow addBookWindow;

    private Book currentlyReading; // refactor this

    private UserGoal userGoal;

    @FXML
    private StackPane commandBoxPlaceholder;

    //@FXML
    //private MenuItem helpMenuItem;

    //@FXML
    //private MenuItem summaryMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane currentlyReadingPlaceholder;

    @FXML
    private StackPane userGoalPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    Button btn_addBook;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        //setAccelerators();

        helpWindow = new HelpWindow();
        addBookWindow = new AddBookWindow(logic);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //private void setAccelerators() {
        //setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    //}


    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    /*private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
    /*    getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }*/

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        bookListPanel = new BookListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(bookListPanel.getRoot());

        currentlyReadingDisplay = new CurrentlyReadingDisplay(logic.getCurrentlyReading());
        currentlyReadingPlaceholder.getChildren().add(currentlyReadingDisplay.getRoot());


        userGoalDisplay = new UserGoalDisplay(logic.getUserGoal());
        userGoalPlaceholder.getChildren().add(userGoalDisplay.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Opens the summary window or focuses on it if it's already opened.
     */
    @FXML
    public void handleAddBook() {
        try {
            logic.execute("summary");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (!addBookWindow.isShowing()) {
            addBookWindow.show();
        } else {
            addBookWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        addBookWindow.hide();
        primaryStage.hide();
    }

    @FXML
    private void addBook(){
        handleAddBook();
    }

    public void setCurrentlyReading(Book currentlyReading) {
        this.currentlyReading = currentlyReading;
        this.currentlyReadingDisplay.update(this.currentlyReading);
    }

    public void setUserGoal(UserGoal userGoal) {
        this.userGoal = userGoal;
        this.userGoalDisplay.update(this.userGoal);
    }

    public BookListPanel getPersonListPanel() {
        return bookListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see tracker.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            //currentlyReadingDisplay.setFeedbackToUser(currentlyReading.toString());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowSummary()) {
                handleAddBook();
            }

            if (commandResult.isExit()) {
                handleExit();
            }
            if (addBookWindow.isShowing()) {
                logic.execute("summary");
                addBookWindow.update();
            }
            setCurrentlyReading(logic.getCurrentlyReading());
            setUserGoal(logic.getUserGoal());
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
