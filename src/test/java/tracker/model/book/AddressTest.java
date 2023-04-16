package tracker.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid addresses
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid addresses
        assertTrue(Note.isValidNote("Blk 456, Den Road, #01-355"));
        assertTrue(Note.isValidNote("-")); // one character
        assertTrue(Note.isValidNote("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
