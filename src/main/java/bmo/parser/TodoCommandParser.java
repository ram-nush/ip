package bmo.parser;

import bmo.command.Command;
import bmo.command.TodoCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>TodoCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.TODO</code>. 
 */
public class TodoCommandParser extends CommandParser {

    TodoCommandParser() {
        super(CommandWord.TODO, new String[]{ "description" });
    }
    
    @Override
    public Command parse(String parameters) throws BmoException {
        String description = parameters;
        
        checkNonEmpty(description, this.paramNames[0], this.commandWord, TaskListParser.TODO_COMMAND_FORMAT);
        
        return new TodoCommand(description);
    }
}