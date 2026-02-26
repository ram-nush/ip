package bmo.task;

/**
 * Represents a specific task with no datetime. A <code>Todo</code> object corresponds
 * to a task represented by its description e.g., <code>read book</code>
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a formatted string with todo properties to be saved in the save file.
     *
     * @return A string matching the save format of the todo.
     */
    @Override
    public String saveString() {
        return String.format("T | %s", super.saveString());
    }

    /**
     * Returns a formatted string with todo properties to be displayed to the user.
     *
     * @return A string matching the displayed format of the todo.
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
