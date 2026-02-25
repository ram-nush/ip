package bmo.command;

import bmo.parser.CommandWord;
import bmo.exception.BmoException;
import bmo.parser.TaskListParser;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents the default command which cannot be resolved to any other command. 
 * An <code>UnknownCommand</code> object corresponds to a command which stores 
 * the UNKNOWN CommandWord e.g., <code>CommandWord.UNKNOWN</code> and the 
 * input given by the user which does not match any command word e.g., 
 * <code>speak</code>
 */
public class UnknownCommand extends Command {

    private String unknownInput;

    public UnknownCommand(String unknownInput) {
        super(CommandWord.UNKNOWN, false);
        this.unknownInput = unknownInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        ui.showDefaultMessage();
        String commandFormats = String.join("\n", TaskListParser.COMMAND_FORMATS);
        String message = String.format(BmoException.BMO_INVALID_COMMAND_MESSAGE, this.unknownInput);
        String suggestion = String.format(BmoException.BMO_INVALID_COMMAND_SUGGESTION, commandFormats);
        throw new BmoException(message, suggestion);
    }
}
