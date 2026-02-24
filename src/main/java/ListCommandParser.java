public class ListCommandParser extends CommandParser {
    
    ListCommandParser() {
        super("list");
    }
    
    @Override
    public Command parse(String parameters) {
        return new ListCommand();
    }
}
