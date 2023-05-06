package tracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tracker.commons.core.LogsCenter;
import tracker.commons.exceptions.DataConversionException;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.ReadOnlyUserPrefs;
import tracker.model.UserGoal;
import tracker.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class TrackerStorageManager implements TrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(TrackerStorageManager.class);
    private BookTrackerStorage bookTrackerStorage;
    private UserGoalStorage userGoalStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code TrackerStorageManager} with the given {@code BookTrackerStorage} and {@code UserPrefStorage}.
     */
    public TrackerStorageManager(BookTrackerStorage bookTrackerStorage, UserPrefsStorage userPrefsStorage, UserGoalStorage userGoalStorage) {
        this.bookTrackerStorage = bookTrackerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userGoalStorage = userGoalStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getBookTrackerFilePath() {
        return bookTrackerStorage.getBookTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyBookTracker> readBookTracker() throws DataConversionException, IOException {
        return readBookTracker(bookTrackerStorage.getBookTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyBookTracker> readBookTracker(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bookTrackerStorage.readBookTracker(filePath);
    }

    @Override
    public void saveBookTracker(ReadOnlyBookTracker bookTracker) throws IOException {
        saveBookTracker(bookTracker, bookTrackerStorage.getBookTrackerFilePath());
    }

    @Override
    public void saveBookTracker(ReadOnlyBookTracker bookTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bookTrackerStorage.saveBookTracker(bookTracker, filePath);
    }

    @Override
    public Path getUserGoalFilePath() {
        return userGoalStorage.getUserGoalFilePath();
    }

    @Override
    public Optional<UserGoal> readUserGoal() throws DataConversionException, IOException {
        return readUserGoal(userGoalStorage.getUserGoalFilePath());
    }

    @Override
    public Optional<UserGoal> readUserGoal(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userGoalStorage.readUserGoal(filePath);
    }

    @Override
    public void saveUserGoal(UserGoal userGoal) throws IOException {
        saveUserGoal(userGoal, userGoalStorage.getUserGoalFilePath());
    }

    @Override
    public void saveUserGoal(UserGoal userGoal, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userGoalStorage.saveUserGoal(userGoal, filePath);
    }

}
