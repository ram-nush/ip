import java.util.ArrayList;
import java.util.List;

public interface CommandParser {
    public Command parse(String parameters);

    public static String[] parseArguments(String arguments, String[] delimiters) {
        List<String> argumentsList = new ArrayList<String>();
        String remainingArgs = arguments;
        String[] parts;
        String argument;

        for (String delimiter : delimiters) {
            parts = remainingArgs.split(delimiter, 2);
            argument = parts[0].strip();
            argumentsList.add(argument);

            remainingArgs = "";
            if (parts.length == 2) {
                remainingArgs = parts[1];
            }
        }
        argumentsList.add(remainingArgs.strip());

        return argumentsList.toArray(new String[0]);
    }

    public static boolean isInRange(int index, TaskList tasks)
            throws BmoException {

        if (!tasks.isInRange(index)) {
            int numTasks = tasks.getTotal();
            if (tasks.isEmpty()) {
                String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
                String exSuggestion = BmoException.BMO_INVALID_INTEGER_SUGGESTION_EMPTY;
                throw new BmoException(exMessage, exSuggestion);
            }

            String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
            String exSuggestion = String.format(BmoException.BMO_INVALID_INTEGER_SUGGESTION_EXISTING,
                    numTasks, numTasks);
            throw new BmoException(exMessage, exSuggestion);
        }

        return true;
    }
}
