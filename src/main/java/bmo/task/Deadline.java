package bmo.task;

import java.time.LocalDateTime;

import bmo.exception.StorageCorruptedException;
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

    /**
     * Returns a formatted string with deadline properties to be saved in the save file.
     *
     * @return A string matching the save format of the deadline.
     */
    @Override
    public String saveString() {
        return String.format("D | %s | %s", super.saveString(),
                this.due.format(StorageParser.OUTPUT_FORMATTER));
    }

    /**
     * Returns a formatted string with deadline properties to be displayed to the user.
     *
     * @return A string matching the displayed format of the deadline.
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(),
                this.due.format(StorageParser.OUTPUT_FORMATTER));
    }
}
