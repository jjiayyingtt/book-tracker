package tracker.model.book;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's date that the user finished reading on.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateFinished extends BookDate {

    public final String value;

    /**
     * Constructs an {@code DateFinished}.
     *
     * @param dateFinished A valid date.
     */
    public DateFinished(String dateFinished) {
        super(dateFinished);
        requireNonNull(dateFinished);
        //checkArgument(isValidDate(dateAdded), MESSAGE_CONSTRAINTS);
        value = dateFinished;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAdded // instanceof handles nulls
                && value.equals(((DateAdded) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
