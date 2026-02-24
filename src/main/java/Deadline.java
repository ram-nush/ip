import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
