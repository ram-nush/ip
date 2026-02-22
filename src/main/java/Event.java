import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");

    public Event(String description, LocalDateTime from, LocalDateTime to) 
            throws MissingArgumentException {
        super(description);
        if (description.isEmpty()) {
            throw new MissingArgumentException("description", "event",
                    "To fix: Add a description after event");
        }
        if (from == null) {
            throw new MissingArgumentException("/from", "event",
                    "To fix: Add a start datetime after /from");
        }
        if (to == null) {
            throw new MissingArgumentException("/to", "event",
                    "To fix: Add an end datetime after /to");
        }
        this.from = from;
        this.to = to;
    }

    @Override
    public String saveString() {
        return "E | " + super.saveString() + " | " 
                + this.from.format(outputFormatter) + " | " 
                + this.to.format(outputFormatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(outputFormatter)
                + " to: " + this.to.format(outputFormatter) + ")";
    }
}
