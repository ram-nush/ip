import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    
    static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
    
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String saveString() {
        return String.format("E | %s | %s | %s", super.saveString(), 
                this.from.format(outputFormatter), this.to.format(outputFormatter));
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), 
                this.from.format(outputFormatter), this.to.format(outputFormatter));
    }
}
