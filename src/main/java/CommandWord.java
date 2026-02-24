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

    public static CommandWord fromString(String commandName) {
        try {
            String commandNameUpperCase = commandName.toUpperCase();
            return CommandWord.valueOf(commandNameUpperCase);
        } catch (IllegalArgumentException e) {
            return CommandWord.UNKNOWN;
        }
    }
}
