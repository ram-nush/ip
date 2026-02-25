package bmo.task;

import java.time.LocalDateTime;

import bmo.storage.StorageParser;

/**
 * Represents a specific task with a start and an end. An <code>Event</code> object 
 * corresponds to a task represented by its description e.g., <code>return book</code>, 
 * a datetime for when the task starts, and a datetime for when the task ends 
 * e.g., <code>5-3-2026 1234</code>
 */
public class Event extends Task {
    
    protected LocalDateTime start;
    protected LocalDateTime end;

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String saveString() {
        return String.format("E | %s | %s | %s", super.saveString(), 
                this.start.format(StorageParser.OUTPUT_FORMATTER), this.end.format(StorageParser.OUTPUT_FORMATTER));
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s, to: %s)", super.toString(),
                this.start.format(StorageParser.OUTPUT_FORMATTER), this.end.format(StorageParser.OUTPUT_FORMATTER));
    }
}
