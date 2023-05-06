package tracker.storage;

import tracker.commons.core.LogsCenter;
import tracker.commons.exceptions.DataConversionException;
import tracker.commons.exceptions.IllegalValueException;
import tracker.commons.util.FileUtil;
import tracker.commons.util.JsonUtil;
import tracker.model.UserGoal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class JsonUserGoalStorage implements UserGoalStorage{

    private static final Logger logger = LogsCenter.getLogger(JsonBookTrackerStorage.class);

    private Path filePath;

    public JsonUserGoalStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserGoalFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserGoal> readUserGoal() throws DataConversionException {
        return readUserGoal(filePath);
    }

    /**
     * Similar to {@link #readUserGoal()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UserGoal> readUserGoal(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonAdaptedGoal> jsonUserGoal = JsonUtil.readJsonFile(
                filePath, JsonAdaptedGoal.class);
        if (!jsonUserGoal.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonUserGoal.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserGoal(UserGoal userGoal) throws IOException {
        saveUserGoal(userGoal, filePath);
    }

    /**
     * Similar to {@link #saveUserGoal(UserGoal)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUserGoal(UserGoal userGoal, Path filePath) throws IOException {
        requireNonNull(userGoal);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonAdaptedGoal(userGoal), filePath);
    }
}
