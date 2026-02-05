public class InvalidIndexException extends BmoException {
    public String suggestString;
    
    public InvalidIndexException(String message, String suggestString) {
        super(message);
        this.suggestString = suggestString;
    }

    public String getSuggestString() {
        return this.suggestString;
    }
}
