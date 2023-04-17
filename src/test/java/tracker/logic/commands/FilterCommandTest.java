package tracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tracker.testutil.TypicalPersons.ALICE;
import static tracker.testutil.TypicalPersons.BENSON;
import static tracker.testutil.TypicalPersons.DANIEL;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tracker.model.Model;
import tracker.model.ModelManager;
import tracker.model.UserPrefs;
import tracker.model.book.BookContainsTagsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        BookContainsTagsPredicate firstPredicate =
                new BookContainsTagsPredicate(Collections.singletonList("first"));
        BookContainsTagsPredicate secondPredicate =
                new BookContainsTagsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 0);
        BookContainsTagsPredicate predicate = preparePredicate(" ");
        FilterCommand command = new FilterCommand(predicate);
        CommandResult result = command.execute(model);
        expectedModel.updateFilteredBookList(predicate);
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 3);
        // NameContainsKeywordsPredicate predicate = preparePredicate("friends");
        BookContainsTagsPredicate predicate = preparePredicate("friends");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredBookList());
    }

    /**
     * Parses {@code userInput} into a {@code BookContainsTagsPredicate}.
     */
    private BookContainsTagsPredicate preparePredicate(String userInput) {
        return new BookContainsTagsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
