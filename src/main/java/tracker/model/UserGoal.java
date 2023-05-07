package tracker.model;

public class UserGoal implements ReadOnlyUserGoal{

    private static UserGoal theOne = null;
    private String goal;
    private String current;
    public UserGoal(String goal, String current) {
        this.goal = goal;
        this.current = current;
    }

    public UserGoal(ReadOnlyUserGoal goalToBeCopied) {
        this.goal = goalToBeCopied.getGoal();
        this.current = goalToBeCopied.getCurrent();
    }

    public UserGoal() {};

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public String getGoal() {
        return goal;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    @Override
    public String getCurrent() {
        return current;
    }
}
