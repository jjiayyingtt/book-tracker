package tracker.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the currently reading display.
 */
public class CurrentlyReadingDisplay extends UiPart<Region> {

    private static final String FXML = "CurrentlyReadingDisplay.fxml";

    @FXML
    private TextArea currentlyReadingDisplay;

    public CurrentlyReadingDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        currentlyReadingDisplay.setText(feedbackToUser);
    }
}
