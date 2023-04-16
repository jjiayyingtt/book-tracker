package tracker.model.book;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's title in the book tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS =
            "Title cannot be blank.";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullTitle;

    /**
     * Constructs a {@code Name}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_CONSTRAINTS);
        fullTitle = title;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && fullTitle.equalsIgnoreCase(((Title) other).fullTitle)); // state check
    }

    @Override
    public int hashCode() {
        return fullTitle.hashCode();
    }

}
