package bmo.task;

/**
 * Represents a task created by the user. A <code>Task</code> object corresponds 
 * to a task represented by its description e.g., <code>read book</code> and whether
 * it has been done e.g., <code>false</code>
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted string based on whether the task is done.
     *
     * @return A string corresponding to whether the task is done.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Returns a formatted string with task properties to be saved in the save file.
     *
     * @return A string matching the save format of the task.
     */
    public String saveString() {
        return (this.isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Returns a formatted string with task properties to be displayed to the user.
     *
     * @return A string matching the displayed format of the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
