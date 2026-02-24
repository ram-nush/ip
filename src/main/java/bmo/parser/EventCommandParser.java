package bmo.parser;

import java.time.LocalDateTime;

import bmo.command.Command;
import bmo.command.EventCommand;
import bmo.exception.BmoException;

public class EventCommandParser extends CommandParser {

    EventCommandParser() {
        super(CommandWord.EVENT, new String[]{ "/from", "/to" }, new String[]{ "description", "start", "end" });
    }
    
    @Override
    public Command parse(String parameters) throws BmoException {
        String[] parts = CommandParser.splitParameters(parameters, this.delimiters);

        String description = parts[0];
        String start = parts[1];
        String end = parts[2];

        checkNonEmpty(description, this.paramNames[0], this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);
        checkNonEmpty(start, this.paramNames[1], this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);
        checkNonEmpty(end, this.paramNames[2], this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);

        LocalDateTime startDateTime = parseDateTime(start, this.paramNames[1],
                this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);
        LocalDateTime endDateTime = parseDateTime(end, this.paramNames[2],
                this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);

        return new EventCommand(description, startDateTime, endDateTime);
    }
}