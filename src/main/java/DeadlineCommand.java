import java.time.LocalDateTime;

public class DeadlineCommand extends Command {

    private String description;
    private LocalDateTime by;

    DeadlineCommand(String description, LocalDateTime by) {
        super(false);
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task deadlineTask = new Deadline(this.description, this.by);
        tasks.addTask(deadlineTask);
        ui.showAddMessage(deadlineTask, tasks);
    }
}
