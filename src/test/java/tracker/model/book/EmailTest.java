package tracker.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateAdded(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new DateAdded(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> DateAdded.isValidDate(null));

        // blank email
        assertFalse(DateAdded.isValidDate("")); // empty string
        assertFalse(DateAdded.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(DateAdded.isValidDate("@example.com")); // missing local part
        assertFalse(DateAdded.isValidDate("peterjackexample.com")); // missing '@' symbol
        assertFalse(DateAdded.isValidDate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(DateAdded.isValidDate("peterjack@-")); // invalid domain name
        assertFalse(DateAdded.isValidDate("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(DateAdded.isValidDate("peter jack@example.com")); // spaces in local part
        assertFalse(DateAdded.isValidDate("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(DateAdded.isValidDate(" peterjack@example.com")); // leading space
        assertFalse(DateAdded.isValidDate("peterjack@example.com ")); // trailing space
        assertFalse(DateAdded.isValidDate("peterjack@@example.com")); // double '@' symbol
        assertFalse(DateAdded.isValidDate("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(DateAdded.isValidDate("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(DateAdded.isValidDate("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(DateAdded.isValidDate("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(DateAdded.isValidDate("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(DateAdded.isValidDate("peterjack@.example.com")); // domain name starts with a period
        assertFalse(DateAdded.isValidDate("peterjack@example.com.")); // domain name ends with a period
        assertFalse(DateAdded.isValidDate("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(DateAdded.isValidDate("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(DateAdded.isValidDate("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(DateAdded.isValidDate("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(DateAdded.isValidDate("PeterJack.1190@example.com")); // period in local part
        assertTrue(DateAdded.isValidDate("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(DateAdded.isValidDate("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(DateAdded.isValidDate("a@bc")); // minimal
        assertTrue(DateAdded.isValidDate("test@localhost")); // alphabets only
        assertTrue(DateAdded.isValidDate("123@145")); // numeric local part and domain name
        assertTrue(DateAdded.isValidDate("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(DateAdded.isValidDate("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(DateAdded.isValidDate("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(DateAdded.isValidDate("e1234567@u.nus.edu")); // more than one period in domain
    }
}
