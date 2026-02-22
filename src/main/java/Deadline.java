import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");

    public Deadline(String description, LocalDateTime by) throws MissingArgumentException {
        super(description);
        if (description.isEmpty()) {
            throw new MissingArgumentException("description", "deadline",
                    "To fix: Add a description after deadline");
        }
        if (by == null) {
            throw new MissingArgumentException("/by", "deadline",
                    "To fix: Add a due datetime after /by");
        }
        this.by = by;
    }

    @Override
    public String saveString() {
        return "D | " + super.saveString() + " | " + this.by.format(outputFormatter);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(outputFormatter) + ")";
    }
}
