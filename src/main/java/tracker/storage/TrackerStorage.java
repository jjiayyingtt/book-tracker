package tracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tracker.commons.exceptions.DataConversionException;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.ReadOnlyUserPrefs;
import tracker.model.UserPrefs;
import tracker.model.UserGoal;

/**
 * API of the TrackerStorage component
 */
public interface TrackerStorage extends BookTrackerStorage, UserPrefsStorage, UserGoalStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getBookTrackerFilePath();

    @Override
    Optional<ReadOnlyBookTracker> readBookTracker() throws DataConversionException, IOException;

    @Override
    void saveBookTracker(ReadOnlyBookTracker bookTracker) throws IOException;

    @Override
    Optional<UserGoal> readUserGoal() throws DataConversionException, IOException;

    @Override
    void saveUserGoal(UserGoal userGoal) throws IOException;

}
