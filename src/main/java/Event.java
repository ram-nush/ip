public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) 
            throws MissingArgumentException {
        super(description);
        if (description.isEmpty()) {
            throw new MissingArgumentException("description", "event",
                    "To fix: Add a description after event");
        }
        if (from.isEmpty()) {
            throw new MissingArgumentException("/from", "event",
                    "To fix: Add a start datetime after /from");
        }
        if (to.isEmpty()) {
            throw new MissingArgumentException("/to", "event",
                    "To fix: Add an end datetime after /to");
        }
        this.from = from;
        this.to = to;
    }

    @Override
    public String saveString() {
        return "E | " + super.saveString() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from
                + " to: " + this.to + ")";
    }
}
