package bmo.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import bmo.command.Command;
import bmo.exception.BmoException;

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
    
    public abstract Command parse(String parameters) throws BmoException;

    public static String[] splitParameters(String parameters, String[] delimiters) {
        List<String> parametersList = new ArrayList<String>();
        String remainingParams = parameters;
        String[] parts;
        String parameter;

        for (String delimiter : delimiters) {
            parts = remainingParams.split(delimiter, 2);
            parameter = parts[0].strip();
            parametersList.add(parameter);

            remainingParams = "";
            if (parts.length == 2) {
                remainingParams = parts[1];
            }
        }
        parametersList.add(remainingParams.strip());

        return parametersList.toArray(new String[0]);
    }

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

    public static LocalDateTime parseDateTime(String parameter, String paramName,
                                              CommandWord commandWord, String commandFormat) throws BmoException {
        LocalDateTime localDateTime;

        try {
            localDateTime = LocalDateTime.parse(parameter, TaskListParser.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                    paramName, commandWord);
            String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                    commandWord, TaskListParser.INPUT_DATETIME_PATTERN, commandFormat);
            throw new BmoException(message, suggestion);
        }

        return localDateTime;
    }

    public static int parseInteger(String parameter) throws BmoException {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            String message = String.format(BmoException.BMO_NOT_INTEGER_MESSAGE, parameter);
            String suggestion = BmoException.BMO_NOT_INTEGER_SUGGESTION;
            throw new BmoException(message, suggestion);
        }
    }

    public static void checkIntegerInRange(int index, int totalTasks) throws BmoException {
        if (totalTasks == 0) {
            String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
            String exSuggestion = BmoException.BMO_INVALID_INTEGER_SUGGESTION_EMPTY;
            throw new BmoException(exMessage, exSuggestion);
        }

        if (index < 1 || index > totalTasks) {
            String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
            String exSuggestion = String.format(BmoException.BMO_INVALID_INTEGER_SUGGESTION_EXISTING,
                    totalTasks, totalTasks);
            throw new BmoException(exMessage, exSuggestion);
        }
    }
}
