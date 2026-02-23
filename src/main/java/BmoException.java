public class BmoException extends Exception {
    public String suggestString;
    
    public BmoException(String message, String suggestString) {
        super(message);
        this.suggestString = suggestString;
    }

    public String getSuggestString() {
        return this.suggestString;
    }
    
    @Override
    public String toString() {
        return this.getMessage();
    }
}
