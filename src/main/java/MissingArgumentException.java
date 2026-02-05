public class MissingArgumentException extends BmoException {
    public MissingArgumentException(String argumentName, String commandName) {
        super("Missing argument " + argumentName + " in " + commandName);
    }
}
