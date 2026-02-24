public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String saveString() {
        return String.format("T | %s", super.saveString());
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
