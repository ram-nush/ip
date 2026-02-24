public class ListCommand extends Command {
    
    ListCommand() {
        super("list", false);
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}
