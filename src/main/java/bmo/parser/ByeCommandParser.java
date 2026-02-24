package bmo.parser;

import bmo.command.ByeCommand;
import bmo.command.Command;

public class ByeCommandParser extends CommandParser {

    ByeCommandParser() {
        super(CommandWord.BYE);
    }
    
    @Override
    public Command parse(String parameters) {
        return new ByeCommand();
    }
}
