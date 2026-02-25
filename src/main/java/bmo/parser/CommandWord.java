package bmo.parser;

/**
 * Represents a command determined by a line of user input. A <code>CommandWord</code> 
 * object corresponds to one of the following specific command types, e.g. 
 * <code>CommandWord.LIST</code>
 */
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
