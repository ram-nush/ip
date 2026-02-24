public class DeleteCommand extends Command {

    private int index;

    DeleteCommand(int index) {
        super(false);
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task deleteTask = tasks.deleteTask(this.index);
        ui.showDeleteMessage(deleteTask, tasks);
    }
}
