package tracker.logic.parser;

import static tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tracker.logic.commands.CommandTestUtil.*;
import static tracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tracker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tracker.testutil.TypicalPersons.AMY;
import static tracker.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import tracker.logic.commands.AddCommand;
import tracker.model.book.Note;
import tracker.model.book.DateAdded;
import tracker.model.book.Title;
import tracker.model.book.Book;
import tracker.model.book.Progress;
import tracker.model.tag.Tag;
import tracker.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB
                + TRANSACTION_COUNT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BUSINESS_SIZE_DESC_BOB
                + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BUSINESS_SIZE_DESC_BOB
                + COMPANY_DESC_BOB + PRIORITY_DESC_BOB
                + TRANSACTION_COUNT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BUSINESS_SIZE_DESC_BOB
                + COMPANY_DESC_BOB + PRIORITY_DESC_BOB
                + TRANSACTION_COUNT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB
                + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Book expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BUSINESS_SIZE_DESC_AMY + COMPANY_DESC_AMY + PRIORITY_DESC_AMY + TRANSACTION_COUNT_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB
                + TRANSACTION_COUNT_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_BUSINESS_SIZE_BOB + VALID_COMPANY_BOB + VALID_PRIORITY_BOB + TRANSACTION_COUNT_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Title.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Progress.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DateAdded.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Note.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB + TRANSACTION_COUNT_DESC_BOB,
                Title.MESSAGE_CONSTRAINTS);



        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BUSINESS_SIZE_DESC_BOB + COMPANY_DESC_BOB + PRIORITY_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
