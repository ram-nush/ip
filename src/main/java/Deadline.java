import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String saveString() {
        return String.format("D | %s | %s", super.saveString(),
                this.by.format(StorageParser.OUTPUT_FORMATTER));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(),
                this.by.format(StorageParser.OUTPUT_FORMATTER));
    }
}
