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
    FIND,
    MARK,
    UNMARK,
    DELETE,
    BYE,
    UNKNOWN;

    /**
     * Returns the lowercase name of the CommandWord
     * to match the format displayed to the user
     *
     * @return A lowercase string corresponding to the CommandWord.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    /**
     * Converts a string to a corresponding CommandWord.
     * If the string does not match any CommandWord, a 
     * default CommandWord is returned e.g., CommandWord.UNKNOWN
     *
     * @param commandName The string to be converted to a CommandWord.
     * @return A lowercase string corresponding to the CommandWord.
     */
    public static CommandWord fromString(String commandName) {
        try {
            String commandNameUpperCase = commandName.toUpperCase();
            return CommandWord.valueOf(commandNameUpperCase);
        } catch (IllegalArgumentException e) {
            return CommandWord.UNKNOWN;
        }
    }
}
