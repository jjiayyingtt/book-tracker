package tracker.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Progress(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Progress(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Progress.isValidProgress(null));

        // invalid phone numbers
        assertFalse(Progress.isValidProgress("")); // empty string
        assertFalse(Progress.isValidProgress(" ")); // spaces only
        assertFalse(Progress.isValidProgress("91")); // less than 3 numbers
        assertFalse(Progress.isValidProgress("phone")); // non-numeric
        assertFalse(Progress.isValidProgress("9011p041")); // alphabets within digits
        assertFalse(Progress.isValidProgress("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Progress.isValidProgress("911")); // exactly 3 numbers
        assertTrue(Progress.isValidProgress("93121534"));
        assertTrue(Progress.isValidProgress("124293842033123")); // long phone numbers
    }
}
