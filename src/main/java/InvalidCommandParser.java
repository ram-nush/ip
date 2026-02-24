public class InvalidCommandParser implements CommandParser {

    @Override
    public Command parse(String parameters) {
        return new InvalidCommand(parameters);
    }
}
