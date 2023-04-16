package tracker.model.book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's date that it was added to the book tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateAdded implements Comparable<DateAdded>{

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param dateAdded A valid date.
     */
    public DateAdded(String dateAdded) {
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

    private Date getDateTime() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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

    @Override
    public int compareTo(DateAdded o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
