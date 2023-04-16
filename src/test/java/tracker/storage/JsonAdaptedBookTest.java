package tracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tracker.storage.JsonAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static tracker.testutil.Assert.assertThrows;
import static tracker.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tracker.commons.exceptions.IllegalValueException;
import tracker.model.book.Note;
import tracker.model.book.Author;
import tracker.model.book.DateAdded;
import tracker.model.book.Title;
import tracker.model.book.Progress;
import tracker.model.book.Category;


public class JsonAdaptedBookTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_BUSINESS_SIZE = "";

    private static final String INVALID_COMPANY = "";
    private static final String INVALID_PRIORITY = "medhigh";

    private static final String INVALID_TRANSACTION_COUNT = "-1";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_BUSINESS_SIZE = BENSON.getBusinessSize().toString();

    private static final String VALID_COMPANY = BENSON.getCompany().toString();

    private static final String VALID_PRIORITY = BENSON.getPriority().toString();
    private static final String VALID_TRANSACTION_COUNT = BENSON.getTransactionCount().toString();



    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBook person =
                new JsonAdaptedBook(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY,
                VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedBook person =
                new JsonAdaptedBook(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUSINESS_SIZE, INVALID_COMPANY, VALID_PRIORITY,
                        VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = Progress.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Progress.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedBook person =
                new JsonAdaptedBook(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = DateAdded.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateAdded.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedBook person =
                new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBusinessSize_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = BusinessSize.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBusinessSize_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BusinessSize.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, null, VALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, VALID_COMPANY, null, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, VALID_COMPANY, INVALID_PRIORITY, VALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionCount_throwsIllegalValueException() {
        JsonAdaptedBook person = new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, INVALID_TRANSACTION_COUNT, VALID_TAGS);
        String expectedMessage = TransactionCount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBook person =
                new JsonAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_BUSINESS_SIZE, VALID_COMPANY, VALID_PRIORITY, VALID_TRANSACTION_COUNT, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
