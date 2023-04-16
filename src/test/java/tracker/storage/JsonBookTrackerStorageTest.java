package tracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tracker.testutil.Assert.assertThrows;
import static tracker.testutil.TypicalPersons.ALICE;
import static tracker.testutil.TypicalPersons.HOON;
import static tracker.testutil.TypicalPersons.IDA;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tracker.commons.exceptions.DataConversionException;
import tracker.model.BookTracker;
import tracker.model.ReadOnlyBookTracker;

public class JsonBookTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBookTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyBookTracker> readAddressBook(String filePath) throws Exception {
        return new JsonBookTrackerTrackerStorage(Paths.get(filePath)).readBookTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        BookTracker original = getTypicalAddressBook();
        JsonBookTrackerTrackerStorage jsonBookTrackerStorage = new JsonBookTrackerTrackerStorage(filePath);

        // Save in new file and read back
        jsonBookTrackerStorage.saveBookTracker(original, filePath);
        ReadOnlyBookTracker readBack = jsonBookTrackerStorage.readBookTracker(filePath).get();
        assertEquals(original, new BookTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBook(HOON);
        original.removeBook(ALICE);
        jsonBookTrackerStorage.saveBookTracker(original, filePath);
        readBack = jsonBookTrackerStorage.readBookTracker(filePath).get();
        assertEquals(original, new BookTracker(readBack));

        // Save and read without specifying file path
        original.addBook(IDA);
        jsonBookTrackerStorage.saveBookTracker(original); // file path not specified
        readBack = jsonBookTrackerStorage.readBookTracker().get(); // file path not specified
        assertEquals(original, new BookTracker(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyBookTracker addressBook, String filePath) {
        try {
            new JsonBookTrackerTrackerStorage(Paths.get(filePath))
                    .saveBookTracker(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new BookTracker(), null));
    }
}
