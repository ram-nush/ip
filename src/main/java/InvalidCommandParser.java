public class InvalidCommandParser extends CommandParser {

    InvalidCommandParser(String commandName) {
        super(commandName);
    }
    
    @Override
    public Command parse(String parameters) {
        return new InvalidCommand(parameters);
    }
}
