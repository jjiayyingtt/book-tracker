package tracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tracker.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tracker.commons.exceptions.IllegalValueException;
import tracker.commons.util.JsonUtil;
import tracker.model.BookTracker;
import tracker.testutil.TypicalPersons;

public class JsonSerializableBookTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBookTrackerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableBookTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableBookTracker.class).get();
        BookTracker addressBookFromFile = dataFromFile.toModelType();
        BookTracker typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBookTracker dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableBookTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableBookTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableBookTracker.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBookTracker.MESSAGE_DUPLICATE_BOOK,
                dataFromFile::toModelType);
    }

}
