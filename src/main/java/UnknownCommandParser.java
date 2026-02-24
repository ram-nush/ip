public class UnknownCommandParser extends CommandParser {

    private String unknownInput;
    
    UnknownCommandParser(String unknownInput) {
        super(CommandWord.UNKNOWN);
        this.unknownInput = unknownInput;
    }
    
    @Override
    public Command parse(String parameters) {
        return new UnknownCommand(this.unknownInput);
    }
}
