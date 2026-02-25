package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.task.Todo;
import bmo.ui.Ui;

/**
 * Represents a command which adds a todo task to the list of tasks. A 
 * <code>TodoCommand</code> object corresponds to a command which stores the 
 * TODO CommandWord e.g., <code>CommandWord.TODO</code>, and a description e.g., 
 * <code>read book</code>
 */
public class TodoCommand extends Command {

    private String description;

    public TodoCommand(String description) {
        super(CommandWord.TODO, false);
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task todoTask = new Todo(this.description);
        tasks.addTask(todoTask);
        ui.showAddMessage(todoTask, tasks);
    }
}
