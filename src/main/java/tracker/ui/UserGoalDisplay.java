package tracker.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import tracker.model.UserGoal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A ui for the currently reading display.
 */
public class UserGoalDisplay extends UiPart<Region> {

    private static final String FXML = "UserGoalDisplay.fxml";

    @FXML
    private Label progress;
    @FXML
    private Label todayDate;


    public UserGoalDisplay(UserGoal userGoal) {
        super(FXML);
        String stringToDisplay = "Reading Challenge:\n" + userGoal.getCurrent() + "/" + userGoal.getGoal();
        progress.setText(stringToDisplay);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        todayDate.setText(dtf.format(now));
    }

    /*public void setCurrentlyReadingDisplay(Book currentlyReading) {
        requireNonNull(feedbackToUser);
        currentlyReadingDisplay.setText(feedbackToUser);
    }*/
}
