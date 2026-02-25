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
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}
