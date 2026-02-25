package bmo.task;

import java.time.LocalDateTime;

import bmo.storage.StorageParser;

/**
 * Represents a specific task with a deadline. A <code>Deadline</code> object 
 * corresponds to a task represented by its description e.g., <code>return book</code> 
 * and a datetime for the task to be completed by e.g., <code>5-3-2026 1234</code>
 */
public class Deadline extends Task {

    protected LocalDateTime due;

    public Deadline(String description, LocalDateTime due) {
        super(description);
        this.due = due;
    }

    @Override
    public String saveString() {
        return String.format("D | %s | %s", super.saveString(),
                this.due.format(StorageParser.OUTPUT_FORMATTER));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(),
                this.due.format(StorageParser.OUTPUT_FORMATTER));
    }
}
