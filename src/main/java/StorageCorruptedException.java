import java.util.List;

public class StorageCorruptedException extends BmoException {

    public static final String BMO_CORRUPTED_LINE_MESSAGE = "The line is corrupted.";
    public static final String BMO_CORRUPTED_LINE_SUGGESTION = "Save it elsewhere before it gets overwritten.";
    public static final String BMO_CORRUPTED_LINES_MESSAGE = "The below lines are corrupted.";
    public static final String BMO_CORRUPTED_LINES_SUGGESTION = "Save them elsewhere before they get overwritten.";
    
    private final List<String> corruptedLines;
    
    public StorageCorruptedException(String message, List<String>corruptedLines) {
        super(message);
        this.corruptedLines = corruptedLines;
    }
    
    public StorageCorruptedException(String message, String suggestion, List<String> corruptedLines) {
        super(message, suggestion);
        this.corruptedLines = corruptedLines;
    }

    @Override
    public String toString() {
        String corruptedText = String.join("\n", this.corruptedLines);
        return String.format("%s\n%s\nTIP: %s", this.getMessage(), corruptedText, super.suggestion);
    }
}
