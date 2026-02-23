public class MissingArgumentException extends BmoException {
    
    public MissingArgumentException(String argumentName, String commandName, String suggestString) {
        super("Missing argument " + argumentName + " in " + commandName, suggestString);
    }
    
    @Override
    public String toString() {
        return "MissingArgumentException: " + super.suggestString;
    }
}
