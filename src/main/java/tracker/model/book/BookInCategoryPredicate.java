package tracker.model.book;

import java.util.function.Predicate;

public class BookInCategoryPredicate implements Predicate<Book> {

    private final String category;

    public BookInCategoryPredicate(String category) {
        this.category = category;
    }
    @Override
    public boolean test(Book book) {
        return book.getCategory().toString().toLowerCase().equals(category);
    }
}
