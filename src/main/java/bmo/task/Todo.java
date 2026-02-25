package bmo.task;

/**
 * Represents a specific task with no datetime. A <code>Todo</code> object corresponds 
 * to a task represented by its description e.g., <code>read book</code>
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String saveString() {
        return String.format("T | %s", super.saveString());
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
