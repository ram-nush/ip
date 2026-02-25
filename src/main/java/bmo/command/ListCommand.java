package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command which prints out the list of tasks. A <code>ListCommand</code> 
 * object corresponds to a command which stores the LIST CommandWord e.g., 
 * <code>CommandWord.LIST</code>
 */
public class ListCommand extends Command {

    public ListCommand() {
        super(CommandWord.LIST, false);
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}
