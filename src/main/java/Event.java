import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
