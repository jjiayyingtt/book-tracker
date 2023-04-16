package tracker.model.book;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's progress in the book tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidProgress(String)}
 */
public class Progress {


    public static final String MESSAGE_CONSTRAINTS =
            "Progress should be >= 0 and <= 100.";
    public static final String VALIDATION_REGEX = "^((100)|(\\d{1,2}(\\.\\d*)?))$";
    public final String value;

    /**
     * Constructs a {@code Progress}.
     *
     * @param progress A valid progress.
     */
    public Progress(String progress) {
        requireNonNull(progress);
        checkArgument(isValidProgress(progress), MESSAGE_CONSTRAINTS);
        value = progress;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidProgress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Progress // instanceof handles nulls
                && value.equals(((Progress) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
