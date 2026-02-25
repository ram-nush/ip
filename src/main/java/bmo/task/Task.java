package bmo.task;

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

    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
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
    
    public String saveString() {
        return (this.isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
