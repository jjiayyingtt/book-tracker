package tracker.model.book;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import tracker.commons.util.StringUtil;

/**
 * Tests that a {@code Book}'s Details matches the keywords given.
 */
public class BookContainsTagsPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public BookContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(
                    book.getTags()
                    .stream()
                    .map(tag -> tag.tagName)
                    .collect(Collectors.joining(" ")), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookContainsTagsPredicate // instanceof handles nulls
                && keywords.equals(((BookContainsTagsPredicate) other).keywords)); // state check
    }

}
