package tracker.model.book;

import java.util.List;
import java.util.function.Predicate;

import tracker.commons.util.StringUtil;

/**
 * Tests that a {@code Book}'s Details matches any of the keywords given.
 */
public class BookContainsKeywordsPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public BookContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getTitle().fullTitle
                        + " " + book.getAuthor().value
                        + " " + book.getCategory().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BookContainsKeywordsPredicate) other).keywords)); // state check
    }

}
