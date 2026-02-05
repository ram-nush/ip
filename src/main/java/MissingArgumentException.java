public class MissingArgumentException extends BmoException {
    public String suggestString;
    
    public MissingArgumentException(String argumentName, String commandName, String suggestString) {
        super("Missing argument " + argumentName + " in " + commandName);
        this.suggestString = suggestString;
    }
    
    public String getSuggestString() {
        return this.suggestString;
    }
}
