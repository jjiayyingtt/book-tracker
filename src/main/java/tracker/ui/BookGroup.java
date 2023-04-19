package tracker.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tracker.model.book.Book;

// give credit to group AY2223S2-CS2103T-T13-1

/**
 * A UI component comprising a group of ModuleCards.
 */
public class BookGroup extends UiPart<Region> {
    private static final String FXML = "BookGroup.fxml";

    @FXML
    private VBox bookGroup;

    @FXML
    private GridPane bookCardGroup;

    /**
     * Instantiates a new BookGroup component.
     *
     * @param books the list of books
     * @param title title of the BookGroup
     */
    public BookGroup(ObservableList<Book> books, String title) {
        super(FXML);
        displayBookCards(books, title);
    }

    /**
     * Displays all BookCards within the BookGroup.
     * @param books the list of books to be displayed as BookCards in the BookGroup.
     */
    public void displayBookCards(ObservableList<Book> books, String title) {
        if (title.length() > 0) {
            Label groupLabel = new Label(title);
            groupLabel.getStyleClass().addAll("book-group-title", (
                    title.length() > 1 ? "book-group-title-long"
                            : "book-group-title-short"), "h4");
            bookGroup.getChildren().add(0, groupLabel);
        }

        /*
         * Position variables to keep track of the current free position (col, row) to add a BookCard on the GridPane.
         *  BookCards are displayed on a `r by 3` grid, where r is the number of rows and 3 is the number of columns.
         *  E.g. If there are 5 BookCards, they will be displayed on a `2 by 3` grid.
         */
        int maxCols = 3;
        int col = 0;
        int row = 0;

        for (Book book : books) {
            BookCard bookCard = new BookCard(book);
            GridPane.setConstraints(bookCard.getRoot(), col, row);
            bookCardGroup.getChildren().add(bookCard.getRoot());

            /* Add placeholder cards to set ModuleCard min width */
            if (col == 0) {
                BookCard placeholderCard1 = new BookCard();
                placeholderCard1.getRoot().getStyleClass().add("module-card-placeholder");
                GridPane.setConstraints(placeholderCard1.getRoot(), 1, row);

                BookCard placeholderCard2 = new BookCard();
                placeholderCard2.getRoot().getStyleClass().add("module-card-placeholder");
                GridPane.setConstraints(placeholderCard2.getRoot(), 2, row);

                bookCardGroup.getChildren().addAll(placeholderCard1.getRoot(), placeholderCard2.getRoot());
            }

            /* Update the position variables */
            col += 1;
            if (col == maxCols) {
                col = 0;
                row += 1;
            }
        }
    }
}
