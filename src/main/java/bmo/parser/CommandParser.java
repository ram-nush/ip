package bmo.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import bmo.command.Command;
import bmo.exception.BmoException;

/**
 * Represents a command parser which parses a string containing all parameters and
 * creates the respective command. A <code>CommandParser</code> object corresponds to
 * the specific CommandWord e.g., <code>CommandWord.LIST</code>, a list of delimiters
 * to separate the parameter by, e.g. <code>/from,/to</code>, and a list of parameter
 * names which represent the parameters given by the user, e.g.<code>description</code>
 */
public abstract class CommandParser {

    protected CommandWord commandWord;
    protected String[] delimiters;
    protected String[] paramNames;

    CommandParser(CommandWord commandWord) {
        this.commandWord = commandWord;
        this.delimiters = new String[0];
        this.paramNames = new String[0];
    }

    CommandParser(CommandWord commandWord, String[] paramNames) {
        this.commandWord = commandWord;
        this.delimiters = new String[0];
        this.paramNames = paramNames;
    }

    CommandParser(CommandWord commandWord, String[] delimiters, String[] paramNames) {
        this.commandWord = commandWord;
        this.delimiters = delimiters;
        this.paramNames = paramNames;
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on these parameters.
     * May throw an exception if the given string does not follow the command format.
     *
     * @param parameters A string containing all parameters
     *                   corresponding to a specific command type.
     * @return A Command object corresponding to the CommandWord.
     * @throws BmoException If parameters does not have all required parameters
     *                      in the correct order.
     */
    public abstract Command parse(String parameters) throws BmoException;

    /**
     * Splits a given string based on an array of delimiters given,
     * in that specific order.
     * Returns an array containing the parameters separated
     * by the above delimiters.
     *
     * @param parameters A string containing parameters.
     * @param delimiters An array of delimiters used to separate the parameters.
     * @return Parameters separated by the delimiters.
     */
    public static String[] splitParameters(String parameters, String[] delimiters) {
        // Create a list to store parameters
        List<String> parametersList = new ArrayList<String>();

        // Initialize variables
        String remainingParams = parameters;
        String[] parts;
        String parameter;

        for (String delimiter : delimiters) {
            // Split the string into two
            parts = remainingParams.split(delimiter, 2);

            // Add first part to parameter list
            parameter = parts[0].strip();
            parametersList.add(parameter);

            // Assign a value to second part
            remainingParams = "";
            if (parts.length == 2) {
                remainingParams = parts[1];
            }
        }

        // Add remaining parameter
        parametersList.add(remainingParams.strip());

        // Return parameter list as an array
        return parametersList.toArray(new String[0]);
    }

    /**
     * Checks whether a given parameter is not empty.
     * If the parameter is empty, a BmoException will be thrown.
     *
     * @param parameter A parameter extracted from splitting user input.
     * @param paramName The name of the parameter.
     * @param commandWord The specific command this parameter is from.
     * @param commandFormat The format of this command.
     * @throws BmoException If parameter is empty.
     */
    public static void checkNonEmpty(String parameter, String paramName,
                                     CommandWord commandWord, String commandFormat) throws BmoException {
        if (parameter.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    paramName, commandWord);
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    commandWord, commandFormat);
            throw new BmoException(message, suggestion);
        }
    }

    /**
     * Checks whether a given parameter can be parsed as a LocalDateTime.
     * Returns a datetime object if the parameter can be parsed.
     * Otherwise, a BmoException will be thrown.
     *
     * @param parameter A parameter extracted from splitting user input.
     * @param paramName The name of the parameter.
     * @param commandWord The specific command this parameter is from.
     * @param commandFormat The format of this command.
     * @return A LocalDateTime object corresponding to parameter.
     * @throws BmoException If parameter cannot be parsed as a LocalDateTime.
     */
    public static LocalDateTime parseDateTime(String parameter, String paramName,
                                              CommandWord commandWord, String commandFormat) throws BmoException {
        LocalDateTime localDateTime;

        try {
            // Parse given parameter as a datetime
            localDateTime = LocalDateTime.parse(parameter, TaskListParser.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            // Parameter does not match a datetime
            String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                    paramName, commandWord);
            String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                    commandWord, TaskListParser.INPUT_DATETIME_PATTERN, commandFormat);
            throw new BmoException(message, suggestion);
        }

        return localDateTime;
    }

    /**
     * Checks whether a given parameter can be parsed as an integer.
     * Returns the integer if the parameter can be parsed.
     * Otherwise, a BmoException will be thrown.
     *
     * @param parameter A parameter extracted from splitting user input.
     * @return Integer corresponding to parameter.
     * @throws BmoException If parameter cannot be parsed as an Integer.
     */
    public static int parseInteger(String parameter) throws BmoException {
        try {
            // Parse given parameter as an Integer
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            // Parameter does not match an integer
            String message = String.format(BmoException.BMO_NOT_INTEGER_MESSAGE, parameter);
            String suggestion = BmoException.BMO_NOT_INTEGER_SUGGESTION;
            throw new BmoException(message, suggestion);
        }
    }

    /**
     * Checks whether a given integer is within 1 and
     * the number of tasks (both inclusive).
     * If the integer is out of range, a BmoException will be thrown.
     *
     * @param index An integer extracted from splitting user input.
     * @param totalTasks The current number of tasks in the list.
     * @throws BmoException If the integer is <= 0 or > totalTasks.
     */
    public static void checkIntegerInRange(int index, int totalTasks) throws BmoException {
        if (totalTasks == 0) {
            // No tasks in list
            String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
            String exSuggestion = BmoException.BMO_INVALID_INTEGER_SUGGESTION_EMPTY;
            throw new BmoException(exMessage, exSuggestion);
        }

        if (index < 1 || index > totalTasks) {
            // Index does not match valid task number
            String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
            String exSuggestion = String.format(BmoException.BMO_INVALID_INTEGER_SUGGESTION_EXISTING,
                    totalTasks, totalTasks);
            throw new BmoException(exMessage, exSuggestion);
        }
    }
}
