public class InvalidIndexException extends BmoException {
    
    public InvalidIndexException(String message, String suggestString) {
        super(message, suggestString);
    }

    @Override
    public String toString() {
        return "InvalidIndexException: " + super.suggestString;
    }
}
