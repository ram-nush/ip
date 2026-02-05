public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) throws MissingArgumentException {
        super(description);
        if (description.isEmpty()) {
            throw new MissingArgumentException("description", "deadline",
                    "To fix: Add a description after deadline");
        }
        if (by.isEmpty()) {
            throw new MissingArgumentException("/by", "deadline",
                    "To fix: Add a due datetime after /by");
        }
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
