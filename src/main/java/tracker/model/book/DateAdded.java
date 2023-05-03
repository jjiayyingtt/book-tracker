package tracker.model.book;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's date that it was added to the book tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateAdded extends BookDate {

    public final String value;

    /**
     * Constructs an {@code DateAdded}.
     *
     * @param dateAdded A valid date.
     */
    public DateAdded(String dateAdded) {
        super(dateAdded);
        requireNonNull(dateAdded);
        //checkArgument(isValidDate(dateAdded), MESSAGE_CONSTRAINTS);
        value = dateAdded;
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
