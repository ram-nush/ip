package bmo.command;

import bmo.exception.BmoException;
import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command which exits the program. A <code>ByeCommand</code> object 
 * corresponds to a command which stores the BYE CommandWord e.g., <code>CommandWord.BYE</code>
 */
public class ByeCommand extends Command {

    public ByeCommand() {
        super(CommandWord.BYE, true);
    }

    /**
     * Displays the list of tasks to the user.
     * Saves the list of tasks in the save file.
     * If the lines cannot be written to the save file, 
     * a custom exception is thrown.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     * @throws BmoException If lines cannot be written to the save file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        String saveText = tasks.saveString();
        ui.showSaveMessage(saveText);
        // can throw bmo.exception.BmoException
        storage.save(saveText);
    }
}
