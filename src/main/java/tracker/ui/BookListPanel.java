package tracker.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tracker.model.book.Book;

import java.util.Map;
import java.util.TreeMap;

/**
 * Panel containing the list of books.
 */
public class BookListPanel extends UiPart<Region> {
    private static final String FXML = "BookListPanel.fxml";

    @FXML
    private ScrollPane bookListScrollContainer;

    @FXML
    private VBox bookList;

    /**
     * Instantiates a new BookListPanel component.
     * @param books the list of books to be displayed on the BookListPanel.
     */
    public BookListPanel(ObservableList<Book> books) {
        super(FXML);

        updateFilteredBooks(books);
    }

    /**
     * Instantiates a new BookListPanel component.
     * @param bookGroups the list of books (categorised in groups) to be displayed on the BookListPanel.
     */
    public BookListPanel(TreeMap<Object, ObservableList<Book>> bookGroups) {
        super(FXML);

        updateSortedBooks(bookGroups);
    }
    /**
     * Updates the list of filtered books in the BookListPanel.
     * @param books the list of filtered books.
     */
    public void updateFilteredBooks(ObservableList<Book> books) {
        bookList.getChildren().clear();

        bookListScrollContainer.setVvalue(0);

        if (books.size() == 0) {
            displayPlaceholderText("No books found that match your search query.");
        }

        BookGroup bookGroup = new BookGroup(books, "");
        bookList.getChildren().add(bookGroup.getRoot());
    }

    /**
     * Updates the list of modules (categorised in groups by a certain sorting criteria) in the BookListPanel.
     * @param bookGroups the list of books categorised in groups.
     */
    public void updateSortedBooks(TreeMap<Object, ObservableList<Book>> bookGroups) {
        bookList.getChildren().clear();

        bookListScrollContainer.setVvalue(0);

        if (bookGroups.size() == 0) {
            displayPlaceholderText("No modules found in the module list.");
        }

        for (Map.Entry<Object, ObservableList<Book>> entry : bookGroups.entrySet()) {
            BookGroup moduleGroup = new BookGroup(entry.getValue(), entry.getKey().toString());
            bookList.getChildren().add(moduleGroup.getRoot());
        }
    }

    /**
     * Displays the placeholder text message when no modules are present in the BookListPanel.
     */
    private void displayPlaceholderText(String text) {
        Label placeholder = new Label(text);
        placeholder.getStyleClass().add("h3");
        bookList.getChildren().add(placeholder);
    }

}
