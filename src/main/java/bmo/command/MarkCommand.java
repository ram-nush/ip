package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command which marks a task in the list of tasks as done. A 
 * <code>MarkCommand</code> object corresponds to a command which stores the 
 * MARK CommandWord e.g., <code>CommandWord.MARK</code>, and an integer which 
 * represents the index of task in the list of tasks to be marked as done e.g., 3
 */
public class MarkCommand extends Command {
    
    private int index;

    public MarkCommand(int index) {
        super(CommandWord.MARK, false);
        this.index = index;
    }

    /**
     * Marks a task corresponding to the index in the task list as done.
     * Displays the information to the user.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task markTask = taskList.markTask(this.index);
        
        ui.showMarkMessage(markTask);
    }
}
