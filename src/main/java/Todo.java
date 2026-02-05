public class Todo extends Task {

    public Todo(String description) throws MissingArgumentException {
        super(description);
        if (description.isEmpty()) {
            throw new MissingArgumentException("description", "todo", 
                    "To fix: Add a description after todo");
        }
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
