package bmo.task;

public class Task {
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
        return this.isDone ? "[X]" : "[ ]";
    }
    
    public String saveString() {
        String isDoneText = this.isDone ? "1" : "0";
        return String.format("%s | %s", isDoneText, this.description);
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getStatusIcon(), this.description);
    }
}
