package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command which deletes a task in the list of tasks. A 
 * <code>DeleteCommand</code> object corresponds to a command which stores 
 * the DELETE CommandWord e.g., <code>CommandWord.DELETE</code>, and an 
 * integer which represents the index of the task in the list of tasks to 
 * be deleted e.g., 1
 */
public class DeleteCommand extends Command {

    private int index;

    public DeleteCommand(int index) {
        super(CommandWord.DELETE, false);
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task deleteTask = tasks.deleteTask(this.index);
        ui.showDeleteMessage(deleteTask, tasks);
    }
}
