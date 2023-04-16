package tracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tracker.testutil.TypicalPersons.CARL;
import static tracker.testutil.TypicalPersons.ELLE;
import static tracker.testutil.TypicalPersons.FIONA;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tracker.model.Model;
import tracker.model.ModelManager;
import tracker.model.UserPrefs;
import tracker.model.book.BookContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        // NameContainsKeywordsPredicate firstPredicate =
        //         new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        // NameContainsKeywordsPredicate secondPredicate =
        //         new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        BookContainsKeywordsPredicate firstPredicate =
                new BookContainsKeywordsPredicate(Collections.singletonList("first"));
        BookContainsKeywordsPredicate secondPredicate =
                new BookContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 0);
        BookContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBookList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 3);
        // NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        BookContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredBookList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BookContainsKeywordsPredicate preparePredicate(String userInput) {
        return new BookContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
