package tracker.model.book;

/**
 * Represents the Author that a book is written by in the book tracker.
 * Guarantees: is valid as declared in {@link #isValidAuthor(String)} (String)}
 */
public class Author {


    public static final String MESSAGE_CONSTRAINTS =
            "Author cannot be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Constructs an {@code Author}.
     *
     * @param author valid author.
     */
    public Author(String author) {
        this.value = String.valueOf(author);
    }

    public static boolean isValidAuthor(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Author // instanceof handles nulls
                && value.equals(((Author) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
