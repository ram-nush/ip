package bmo.command;

import bmo.exception.BmoException;
import bmo.parser.CommandWord;
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

    /**
     * Displays a message to the user that the input does not match any command.
     * Displays the list of commands and their formats to the user.
     * Throws a custom exception to handle the unknown input.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     * @throws BmoException This command does not match any other command.
     */
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
