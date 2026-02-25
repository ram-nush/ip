package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command which marks a task in the list of tasks as not done. 
 * A <code>UnmarkCommand</code> object corresponds to a command which stores the 
 * UNMARK CommandWord e.g., <code>CommandWord.UNMARK</code>, and an integer which 
 * represents the index of task in the list of tasks to be marked as not done e.g., 2
 */
public class UnmarkCommand extends Command {

    private int index;

    public UnmarkCommand(int index) {
        super(CommandWord.UNMARK,false);
        this.index = index;
    }

    /**
     * Marks a task corresponding to the index in the task list as not done.
     * Displays the information to the user.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task unmarkTask = taskList.unmarkTask(this.index);
        
        ui.showUnmarkMessage(unmarkTask);
    }
}
