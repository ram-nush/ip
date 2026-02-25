package bmo.exception;

import java.util.List;

/**
 * Represents a custom exception which is called whenever the program reads lines 
 * from a save file which do not match the expected storage format. A 
 * <code>StorageCorruptedException</code> object corresponds to a message explaining 
 * the reason for an exception e.g., <code>The line is corrupted.</code>, a suggestion 
 * explaining how the user can resolve this exception e.g., <code>Save it elsewhere 
 * before it gets overwritten.</code>, and the corrupted line itself e.g., 
 * <code>T | x read | book | today</code>
 */
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

    /**
     * Returns a formatted string containing the exception message,
     * a suggestion to resolve the exception, and the list of 
     * corrupted lines for the user to save them somewhere else.
     *
     * @return A string with more information on the exception.
     */
    @Override
    public String toString() {
        String corruptedText = String.join("\n", this.corruptedLines);
        return String.format("%s\n%s\nTIP: %s", this.getMessage(), corruptedText, super.suggestion);
    }
}
