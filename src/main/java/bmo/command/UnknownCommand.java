package bmo.command;

import bmo.exception.BmoException;
import bmo.parser.CommandWord;
import bmo.parser.TaskListParser;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class UnknownCommand extends Command {

    private String unknownInput;

    public UnknownCommand(String unknownInput) {
        super(CommandWord.UNKNOWN, false);
        this.unknownInput = unknownInput;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws BmoException {
        // Display user input does not match a command to user
        ui.showDefaultMessage();
        
        // Store the list of command formats as a message
        String commandFormatsText = String.join("\n", TaskListParser.COMMAND_FORMATS);
        
        // Create an exception to handle the unknown command
        String message = String.format(BmoException.BMO_INVALID_COMMAND_MESSAGE, this.unknownInput);
        String suggestion = String.format(BmoException.BMO_INVALID_COMMAND_SUGGESTION, commandFormatsText);
        throw new BmoException(message, suggestion);
    }
}
