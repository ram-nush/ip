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

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        String saveText = tasks.saveString();
        ui.showSaveMessage(saveText);
        // can throw bmo.exception.BmoException
        storage.save(saveText);
    }
}
