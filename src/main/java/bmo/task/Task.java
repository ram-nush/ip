package bmo.task;

/**
 * Represents a task created by the user. A <code>Task</code> object corresponds 
 * to a task represented by its description e.g., <code>read book</code> and whether
 * it has been done e.g., <code>false</code>
 */
public class Task {
    public static final String REMOVE_SYMBOL_REGEX = "[^a-zA-Z0-9]";
    
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted string based on whether the task is done.
     *
     * @return A string corresponding to whether the task is done.
     */
    public String getStatusIcon() {
        return this.isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns whether the description of this task contains the provided keyword.
     * This match is case-insensitive and ignores non-alphanumeric characters.
     * 
     * @param keyword a String that represents the keyword to match
     * @return true if the description contains the keyword, otherwise false
     */
    public boolean hasMatch(String keyword) {
        // Remove non-alphanumeric characters and converts description to lowercase
        String cleanedDescription = this.description.replaceAll(REMOVE_SYMBOL_REGEX, "")
                .toLowerCase();

        // Remove non-alphanumeric characters and converts keyword to lowercase
        String cleanedKeyword = keyword.replaceAll(REMOVE_SYMBOL_REGEX, "")
                .toLowerCase();
        
        // Checks whether the cleaned description contains the cleaned keyword
        return cleanedDescription.contains(cleanedKeyword);
    }

    /**
     * Returns a formatted string with task properties to be saved in the save file.
     *
     * @return A string matching the save format of the task.
     */
    public String saveString() {
        String isDoneText = this.isDone ? "1" : "0";
        return String.format("%s | %s", isDoneText, this.description);
    }

    /**
     * Returns a formatted string with task properties to be displayed to the user.
     *
     * @return A string matching the displayed format of the task.
     */
    @Override
    public String toString() {
        return String.format("%s %s", this.getStatusIcon(), this.description);
    }
}
