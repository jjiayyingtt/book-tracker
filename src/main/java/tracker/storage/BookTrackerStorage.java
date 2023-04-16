package tracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tracker.commons.exceptions.DataConversionException;
import tracker.model.BookTracker;
import tracker.model.ReadOnlyBookTracker;

/**
 * Represents a storage for {@link BookTracker}.
 */
public interface BookTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBookTrackerFilePath();

    /**
     * Returns BookTracker data as a {@link ReadOnlyBookTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBookTracker> readBookTracker() throws DataConversionException, IOException;

    /**
     * @see #getBookTrackerFilePath()
     */
    Optional<ReadOnlyBookTracker> readBookTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBookTracker} to the storage.
     * @param bookTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBookTracker(ReadOnlyBookTracker bookTracker) throws IOException;

    /**
     * @see #saveBookTracker(ReadOnlyBookTracker)
     */
    void saveBookTracker(ReadOnlyBookTracker bookTracker, Path filePath) throws IOException;

}
