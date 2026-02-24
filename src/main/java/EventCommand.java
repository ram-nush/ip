import java.time.LocalDateTime;

public class EventCommand extends Command {

    private String description;
    private LocalDateTime start;
    private LocalDateTime end;

    EventCommand(String description, LocalDateTime start, LocalDateTime end) {
        super("event", false);
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task eventTask = new Event(this.description, this.start, this.end);
        tasks.addTask(eventTask);
        ui.showAddMessage(eventTask, tasks);
    }
}
