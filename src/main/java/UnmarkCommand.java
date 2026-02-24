public class UnmarkCommand extends Command {

    private int index;

    UnmarkCommand(int index) {
        super(false);
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task unmarkTask = tasks.unmarkTask(this.index);
        ui.showUnmarkMessage(unmarkTask);
    }
}
