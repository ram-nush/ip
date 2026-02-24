public class MarkCommand extends Command {
    
    private int index;

    MarkCommand(int index) {
        super(false);
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task markTask = tasks.markTask(this.index);
        ui.showMarkMessage(markTask);
    }
}
