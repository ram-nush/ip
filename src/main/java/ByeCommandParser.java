public class ByeCommandParser extends CommandParser {

    ByeCommandParser() {
        super("bye");
    }
    
    @Override
    public Command parse(String parameters) {
        return new ByeCommand();
    }
}
