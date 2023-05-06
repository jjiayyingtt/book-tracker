package tracker.model;

import javafx.collections.ObservableList;
import tracker.model.book.Book;

public interface ReadOnlyUserGoal {

    String getGoal();

    String getCurrent();
}
