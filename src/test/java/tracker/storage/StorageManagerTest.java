package tracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tracker.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tracker.commons.core.GuiSettings;
import tracker.model.BookTracker;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private TrackerStorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonBookTrackerStorage addressBookStorage = new JsonBookTrackerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new TrackerStorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the TrackerStorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the TrackerStorageManager is properly wired to the
         * {@link JsonBookTrackerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonBookTrackerStorageTest} class.
         */
        BookTracker original = getTypicalAddressBook();
        storageManager.saveBookTracker(original);
        ReadOnlyBookTracker retrieved = storageManager.readBookTracker().get();
        assertEquals(original, new BookTracker(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getBookTrackerFilePath());
    }

}
