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

    /**
     * Initializes a <code>Deadline</code> object which stores the description of the deadline
     * and the date and time that it is due.
     *
     * @param description The String containing a description of the deadline
     * @param due The LocalDateTime corresponding to the due date and time of the deadline
     */
    public Deadline(String description, LocalDateTime due) {
        super(description);

        assert due != null : "Due datetime cannot be null";

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
