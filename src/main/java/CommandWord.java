public enum CommandWord {
    LIST,
    TODO,
    DEADLINE,
    EVENT,
    MARK,
    UNMARK,
    DELETE,
    BYE,
    UNKNOWN;
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
