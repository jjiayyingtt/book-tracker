package tracker.model.book;

import static java.util.Objects.requireNonNull;
import static tracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {
    // TODO: 9/4/2023 change this to non required field

    public static final String MESSAGE_CONSTRAINTS = "Note can take any values.";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_CONSTRAINTS);
        value = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
