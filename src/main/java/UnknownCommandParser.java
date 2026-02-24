public class UnknownCommandParser extends CommandParser {

    UnknownCommandParser(String commandName) {
        super(commandName);
    }
    
    @Override
    public Command parse(String parameters) {
        return new UnknownCommand(parameters);
    }
}
