package tracker.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tracker.commons.core.LogsCenter;
import tracker.commons.exceptions.DataConversionException;
import tracker.commons.exceptions.IllegalValueException;
import tracker.commons.util.FileUtil;
import tracker.commons.util.JsonUtil;
import tracker.model.ReadOnlyBookTracker;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonBookTrackerStorage implements BookTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBookTrackerStorage.class);

    private Path filePath;

    public JsonBookTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBookTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBookTracker> readBookTracker() throws DataConversionException {
        return readBookTracker(filePath);
    }

    /**
     * Similar to {@link #readBookTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBookTracker> readBookTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableBookTracker> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableBookTracker.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBookTracker(ReadOnlyBookTracker bookTracker) throws IOException {
        saveBookTracker(bookTracker, filePath);
    }

    /**
     * Similar to {@link #saveBookTracker(ReadOnlyBookTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBookTracker(ReadOnlyBookTracker bookTracker, Path filePath) throws IOException {
        requireNonNull(bookTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBookTracker(bookTracker), filePath);
    }

}
