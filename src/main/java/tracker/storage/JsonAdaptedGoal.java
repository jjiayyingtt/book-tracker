package tracker.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import tracker.commons.exceptions.IllegalValueException;
import tracker.model.UserGoal;

public class JsonAdaptedGoal {

    private String goal;
    private String current;

    @JsonCreator
    public JsonAdaptedGoal(@JsonProperty("goal") String goal, @JsonProperty("current") String current) {
        this.goal = goal;
        this.current = current;
    }

    public JsonAdaptedGoal(UserGoal source) {
        goal = source.getGoal();
        current = source.getCurrent();
    }

    public UserGoal toModelType() throws IllegalValueException {
        // checks whether goal is valid here
        /*
        if (!UserGoal.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        } */
        return new UserGoal(goal, current);
    }
}
