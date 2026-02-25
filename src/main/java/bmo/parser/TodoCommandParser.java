package bmo.parser;

import bmo.command.Command;
import bmo.command.TodoCommand;
import bmo.exception.BmoException;

public class TodoCommandParser extends CommandParser {

    TodoCommandParser() {
        super(CommandWord.TODO, new String[]{ "description" });
    }
    
    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract description parameter
        String description = parameters;

        // Ensure description is a valid parameter
        CommandParser.checkNonEmpty(description, this.paramNames[0], this.commandWord, 
                TaskListParser.TODO_COMMAND_FORMAT);
        
        return new TodoCommand(description);
    }
}