package bmo.command;

import bmo.exception.BmoException;
import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

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
    
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws BmoException;
}
