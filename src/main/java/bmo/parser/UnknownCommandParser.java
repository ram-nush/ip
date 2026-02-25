package bmo.parser;

import bmo.command.Command;
import bmo.command.UnknownCommand;

/**
 * Represents the default command parser which returns the respective command.
 * An <code>UnknownCommandParser</code> object corresponds to a parser 
 * for a command with the specific CommandWord e.g., <code>CommandWord.UNKNOWN</code>. 
 */
public class UnknownCommandParser extends CommandParser {

    private String unknownInput;
    
    UnknownCommandParser(String unknownInput) {
        super(CommandWord.UNKNOWN);
        this.unknownInput = unknownInput;
    }
    
    @Override
    public Command parse(String parameters) {
        return new UnknownCommand(this.unknownInput);
    }
}
