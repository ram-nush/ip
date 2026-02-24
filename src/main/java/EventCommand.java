import java.time.LocalDateTime;

public class EventCommand extends Command {

    private String description;
    private LocalDateTime from;
    private LocalDateTime to;

    EventCommand(String description, LocalDateTime from, LocalDateTime to) {
        super(false);
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task eventTask = new Event(this.description, this.from, this.to);
        tasks.addTask(eventTask);
        ui.showAddMessage(eventTask, tasks);
    }
}
