package bmo.exception;

public class BmoException extends Exception {
    
    public static final String BMO_NOT_INTEGER_MESSAGE = "%s is not a valid integer!";
    public static final String BMO_NOT_INTEGER_SUGGESTION = "Try using a digit like 2 instead of text.";
    
    public static final String BMO_INVALID_INTEGER_MESSAGE = "Task number %d does not exist!";
    public static final String BMO_INVALID_INTEGER_SUGGESTION_EMPTY = "You currently have no tasks!"
            + " Add a task first.";
    public static final String BMO_INVALID_INTEGER_SUGGESTION_EXISTING = "You currently have %d tasks."
            + " Choose a number between 1 and %d.";
    
    public static final String BMO_INVALID_COMMAND_MESSAGE = "%s is not a valid command type!";
    public static final String BMO_INVALID_COMMAND_SUGGESTION = "Enter one of the following commands"
            + " in the given format.\n%s";
    
    public static final String BMO_MISSING_PARAMS_MESSAGE = "Missing parameter %s in %s command!";
    public static final String BMO_MISSING_PARAMS_SUGGESTION = "Follow the format of the %s command!\n%s";
    
    public static final String BMO_STORE_DATETIME_MESSAGE = "Incorrect input datetime format"
            + " for parameter %s in %s command!";
    public static final String BMO_STORE_DATETIME_SUGGESTION = "Follow the input datetime format"
            + " of the %s command!\ne.g. %s\n%s";
    
    public static final String BMO_RETRIEVE_DATETIME_MESSAGE = "Incorrect saved datetime format!";
    public static final String BMO_RETRIEVE_DATETIME_SUGGESTION = "Follow the saved datetime format"
            + " in the save file!\ne.g. %s";
    
    public static final String BMO_WRITE_FILE_MESSAGE = "Issues with saving to file at %s";
    public static final String BMO_WRITE_FILE_SUGGESTION_VIEW = "Check this file to see the tasks saved.";
    public static final String BMO_FILE_SUGGESTION_EXIST = "Check whether this file exists.";
    
    public static final String BMO_READ_FILE_MESSAGE = "Issues with loading from file at %s";
    public static final String BMO_READ_FILE_SUGGESTION_VIEW = "Check this file for corrupted tasks.";
    
    protected final String suggestion;
    
    public BmoException(String message) {
        this(message, "");
    }
    
    public BmoException(String message, String suggestion) {
        super(message);
        this.suggestion = suggestion;
    }
    
    @Override
    public String toString() {
        return String.format("%s\nTIP: %s", this.getMessage(), this.suggestion);
    }
}
