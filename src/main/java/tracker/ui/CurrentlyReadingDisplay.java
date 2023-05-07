package tracker.ui;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import tracker.model.book.Book;

/**
 * A ui for the currently reading display.
 */
public class CurrentlyReadingDisplay extends UiPart<Region> {

    private static final String FXML = "CurrentlyReadingDisplay.fxml";

    @FXML
    private Label title;
    @FXML
    private ProgressBar progressBar;


    public CurrentlyReadingDisplay(Book currentlyReading) {
        super(FXML);
        update(currentlyReading);
    }

    public void update(Book currentlyReading) {
        if (currentlyReading != null) {
            String stringToDisplay = "Currently Reading:\n" + currentlyReading.getTitle().toString();
            title.setText(stringToDisplay);
            progressBar.setProgress(Double.parseDouble(currentlyReading.getProgress().value) / 100);
        } else {
            String stringToDisplay = "Currently Reading:\n";
            title.setText(stringToDisplay);
            progressBar.setProgress(0 / 100);
        }
    }


}
