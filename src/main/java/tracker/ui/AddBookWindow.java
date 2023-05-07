package tracker.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tracker.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class AddBookWindow extends UiPart<Stage> {

    public static final String TITLE_MESSAGE = "Title: ";
    public static final String AUTHOR_MESSAGE= "Author name: ";
    public static final String NOTE = "Note: ";

    private static final Logger logger = LogsCenter.getLogger(AddBookWindow.class);
    private static final String FXML = "AddBookWindow.fxml";
    private static int SIZE;
    private static String POTENTIAL_EARNINGS;
    private static String COMPANIES;
    private static String TAGS;

    @FXML
    private TextField titleInp;


    @FXML
    private TextField totalPageInp;

    @FXML
    private TextField authorInp;

    @FXML
    private TextField pageReadInp;

    @FXML
    private ChoiceBox categoryInp;

    @FXML
    private ComboBox tagsInp;

    @FXML
    private DatePicker datestartedInp;

    @FXML
    private DatePicker datefinishedInp;

    @FXML
    private TextField ratingInp;

    @FXML
    private TextField notesInp;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public AddBookWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public AddBookWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing summary page of the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Update the summary window.
     */
    public void update() {
        logger.fine("Updating summary page of the application.");
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    public void addBook() {
        String title = titleInp.getText();
        String totalPage = totalPageInp.getText();
        String author = authorInp.getText();
        String pageRead = pageReadInp.getText();
        String category = categoryInp.getSelectionModel().getSelectedItem().toString();
        //ObservableList<String> tags = tagsInp.getItems(); //tags doesnt work for now
        LocalDate dateStarted = datestartedInp.getValue();
        LocalDate dateFinished = datefinishedInp.getValue();
        String rating = ratingInp.getText();
        String notes = notesInp.getText();

    }

}
