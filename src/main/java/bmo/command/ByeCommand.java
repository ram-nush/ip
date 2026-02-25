package bmo.command;

import bmo.exception.BmoException;
import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class ByeCommand extends Command {

    public ByeCommand() {
        super(CommandWord.BYE, true);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws BmoException {
        // Store lines of text to be stored
        String saveText = taskList.saveString();
        
        // Display lines of text to be stored to user
        ui.showSaveMessage(saveText);
        
        // Store the lines in the save file
        storage.save(saveText);
    }
}
