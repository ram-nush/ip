package bmo.command;

import bmo.exception.BmoException;
import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command created from a line of user input. A <code>Command</code> object 
 * corresponds to any command represented by a CommandWord e.g., <code>CommandWord.LIST</code>
 */
public abstract class Command {
    protected CommandWord commandWord;
    protected boolean isExit;
    
    Command(CommandWord commandWord, boolean isExit) {
        this.commandWord = commandWord;
        this.isExit = isExit;
    }
    
    public boolean isExit() {
        return this.isExit;
    }
    
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException;
}
