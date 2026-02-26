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

    /**
     * Initializes a <code>TodoCommand</code> object which stores command parameters
     * representing the description of the task.
     *
     * @param description The String containing a description of the task
     */
    public TodoCommand(String description) {
        super(CommandWord.TODO, false);
        this.description = description;
    }

    /**
     * Creates a Todo object.
     * Adds the Task to the task list.
     * Displays the information to the user.
     *
     * @param taskList The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task todoTask = new Todo(this.description);

        taskList.addTask(todoTask);

        ui.showAddMessage(todoTask, taskList);
    }
}
