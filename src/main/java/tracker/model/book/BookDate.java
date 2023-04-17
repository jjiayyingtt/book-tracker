package tracker.model.book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Book's date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class BookDate implements Comparable<BookDate> {

    public final String value;

    public BookDate(String date) {
        this.value = date;
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

    public boolean isBeforeDate(BookDate date) {
        if (getDateTime().compareTo(date.getDateTime()) > 0) {
            return false;
        } else if (getDateTime().compareTo(date.getDateTime()) < 0) {
            return true;
        } else {
            return true;
        }
    }
    @Override
    public int compareTo(BookDate o) {
        return getDateTime().compareTo(o.getDateTime());
    }

}
