package tracker.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tracker.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class SummaryWindow extends UiPart<Stage> {

    public static final String TITLE_MESSAGE = "Title: ";
    public static final String AUTHOR_MESSAGE= "Author name: ";
    public static final String NOTE = "Note: ";

    private static final Logger logger = LogsCenter.getLogger(SummaryWindow.class);
    private static final String FXML = "SummaryWindow.fxml";
    private static int SIZE;
    private static String POTENTIAL_EARNINGS;
    private static String COMPANIES;
    private static String TAGS;
    @FXML
    private Label countMessage;

    @FXML
    private Label tagMessage;

    @FXML
    private Label potentialEarnings;
    @FXML
    private Label companies;
    @FXML
    private Label tags;
    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public SummaryWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public SummaryWindow() {
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

}
