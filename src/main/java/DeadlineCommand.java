import java.time.LocalDateTime;

public class DeadlineCommand extends Command {

    private String description;
    private LocalDateTime due;

    DeadlineCommand(String description, LocalDateTime due) {
        super(CommandWord.DEADLINE, false);
        this.description = description;
        this.due = due;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task deadlineTask = new Deadline(this.description, this.due);
        tasks.addTask(deadlineTask);
        ui.showAddMessage(deadlineTask, tasks);
    }
}
