package tracker.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tracker.model.book.Book;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    //public final Book book;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label author;
    @FXML
    private Label category;
    @FXML
    private Label dateAdded;
    @FXML
    private Label dates;
    @FXML
    private Label rating;

    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public BookCard(Book book, int displayedIndex) {
        super(FXML);
        //this.book = book;
        fillCard(book);
    }

    /**
     * Instantiates a new placeholder BookCard component. Purpose of this placeholder is to set the
     * max width of the actual BookCards in BookGroup.
     */
    public BookCard() {
        super(FXML);
    }

    /**
     * Populates the BookCard with its information (book code, credits, semyear, grade, tags)
     * @param book The book object that encapsulates the book information.
     */
    private void fillCard(Book book) {
        //id.setText(displayedIndex + ". ");
        title.setText("Title: " + book.getTitle().fullTitle);
        author.setText("Author: " + book.getAuthor().value);
        //note.setText("Note: " + book.getNote().value);
        category.setText("Category: " + book.getCategory().value);
        //progress.setText("Progress: " + book.getProgress().value + "%");
        dateAdded.setText("Date Added: " + book.getDateAdded().value);
        String datesText = "Date Started: " + book.getDateStarted().value + " | " + "Date Finished: " + book.getDateFinished().value;
        dates.setText(datesText);
        rating.setText("Rating: " + book.getRating().value);
        book.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /*
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return id.getText().equals(card.id.getText())
                && book.equals(card.book);
    }*/
}
