package bmo.parser;

import java.time.LocalDateTime;

import bmo.command.Command;
import bmo.command.DeadlineCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>DeadlineCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.DEADLINE</code>. 
 */
public class DeadlineCommandParser extends CommandParser {

    DeadlineCommandParser() {
        super(CommandWord.DEADLINE, new String[]{ "/by" }, new String[]{ "description", "due" });
    }
    
    @Override
    public Command parse(String parameters) throws BmoException {
        String[] parts = CommandParser.splitParameters(parameters, this.delimiters);
        
        String description = parts[0];
        String due = parts[1];

        checkNonEmpty(description, this.paramNames[0], this.commandWord, TaskListParser.DEADLINE_COMMAND_FORMAT);
        checkNonEmpty(due, this.paramNames[1], this.commandWord, TaskListParser.DEADLINE_COMMAND_FORMAT);
        
        LocalDateTime dueDateTime = parseDateTime(due, this.paramNames[1], 
            this.commandWord, TaskListParser.DEADLINE_COMMAND_FORMAT);

        return new DeadlineCommand(description, dueDateTime);
    }
}