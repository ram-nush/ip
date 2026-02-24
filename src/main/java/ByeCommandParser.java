public class ByeCommandParser implements CommandParser {

    @Override
    public Command parse(String parameters) {
        return new ByeCommand();
    }
}
