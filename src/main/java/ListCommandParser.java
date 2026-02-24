public class ListCommandParser implements CommandParser {
    
    @Override
    public Command parse(String parameters) {
        return new ListCommand();
    }
}
