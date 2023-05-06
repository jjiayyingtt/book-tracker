package tracker.storage;

import tracker.commons.exceptions.DataConversionException;
import tracker.model.UserGoal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface UserGoalStorage {

    Path getUserGoalFilePath();

    Optional<UserGoal> readUserGoal() throws DataConversionException, IOException;

    Optional<UserGoal> readUserGoal(Path filePath) throws DataConversionException, IOException;

    void saveUserGoal(UserGoal userGoal) throws IOException;

    void saveUserGoal(UserGoal userGoal, Path filePath) throws IOException;
}
