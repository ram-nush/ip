public class ListCommandParser extends CommandParser {
    
    ListCommandParser() {
        super(CommandWord.LIST);
    }
    
    @Override
    public Command parse(String parameters) {
        return new ListCommand();
    }
}
