public class TodoCommand extends Command {

    private String description;
    
    TodoCommand(String description) {
        super(false);
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task todoTask = new Todo(this.description);
        tasks.addTask(todoTask);
        ui.showAddMessage(todoTask, tasks);
    }
}
