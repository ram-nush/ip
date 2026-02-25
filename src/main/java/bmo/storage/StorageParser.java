package bmo.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import bmo.exception.BmoException;
import bmo.exception.StorageCorruptedException;
import bmo.task.Deadline;
import bmo.task.Event;
import bmo.task.Task;
import bmo.task.Todo;

/**
 * Represents a parser which parses a line from the save file to create the respective 
 * task. A <code>StorageParser</code> object passes a <code>Task</code> object
 * corresponding to the line e.g. <code>new Todo("return book")</code>
 */
public class StorageParser {
    
    public static final String SPLIT_REGEX = "\\s*\\|\\s*";
    
    public static final String OUTPUT_DATETIME_PATTERN = "MMM d uuuu HHmm";
    public static final DateTimeFormatter OUTPUT_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(OUTPUT_DATETIME_PATTERN)
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Reads one line from the save file and creates a task.
     * Returns the task created.
     * If the line is corrupted, a StorageCorruptedException will be thrown.
     *
     * @param line One line retrieved from the save file.
     * @return Task created from the line.
     * @throws StorageCorruptedException If the line does not match any task format.
     */
    public static Task parseLine(String line) throws StorageCorruptedException {
        String[] parts = line.split(SPLIT_REGEX, 2);
        String type = parts[0];
        String storedParameters = "";
        if (parts.length == 2) {
            storedParameters = parts[1];
        }
        
        String[] parameters = storedParameters.split(SPLIT_REGEX);
        Task task;
        
        try {
            switch (type) {
            case "T":
                task = parseTodo(parameters);
                break;

            case "D":
                task = parseDeadline(parameters);
                break;

            case "E":
                task = parseEvent(parameters);
                break;

            default:
                // invalid task type, throw bmo.exception.StorageCorruptedException to bmo.storage.Storage
                // need to inform user that these lines are corrupted
                String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
                String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
                List<String> list = List.<String>of(parts);
                throw new StorageCorruptedException(message, suggestion, list);
            }
        } catch (StorageCorruptedException e) {
            // invalid task parameters, thrown from parsing specific tasks
            // no additional info required, throw as it is
            throw e;
        }
        
        return task;
    }

    /**
     * Checks whether a storage object has corrupted lines.
     * If so, a StorageCorruptedException will be thrown.
     *
     * @param storage The storage object.
     * @throws StorageCorruptedException If storage has non-empty corruptedLines.
     */
    public static void checkCorruptedLinesExist(Storage storage) throws StorageCorruptedException {
        if (storage.hasCorruptedLines()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINES_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINES_SUGGESTION;
            List<String> corruptedLines = storage.getCorruptedLines();
            throw new StorageCorruptedException(message, suggestion, corruptedLines);
        }
    }

    /**
     * Checks whether the number of parameters match the expected number.
     * If they are not equal, a StorageCorruptedException containing the full line 
     * will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @param expectedLength The expected number of parameters.
     * @throws StorageCorruptedException If parameters.length does not match expectedLength.
     */
    public static void checkParamsLength(String[] parameters, int expectedLength) throws StorageCorruptedException {
        if (parameters.length != expectedLength) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
    }

    /**
     * Checks whether the string matches a valid format for storing isDone.
     * If the string does not match either done or not done,
     * a StorageCorruptedException containing the full line will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @param isDone A string extracted from the save file.
     * @throws StorageCorruptedException If isDone is not "0" and not "1".
     */
    public static void checkIsDoneValid(String[] parameters, String isDone) throws StorageCorruptedException {
        if (!isDone.equals("0") && !isDone.equals("1")) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
    }

    /**
     * Checks whether the given description is valid.
     * If the description is empty, a StorageCorruptedException containing 
     * the full line will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @param description A string extracted from the save file.
     * @throws StorageCorruptedException If description is empty.
     */
    public static void checkDescriptionValid(String[] parameters, String description) throws StorageCorruptedException {
        if (description.isEmpty()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
    }

    /**
     * Checks whether a given parameter can be parsed as a LocalDateTime.
     * Returns a datetime object if the parameter can be parsed.
     * Otherwise, a StorageCorruptedException containing the full line will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @param dateTime A parameter extracted from splitting user input.
     * @return A LocalDateTime object corresponding to dateTime.
     * @throws StorageCorruptedException If dateTime cannot be parsed as a LocalDateTime.
     */
    public static LocalDateTime parseDateTime(String[] parameters, String dateTime) throws StorageCorruptedException {
        LocalDateTime localDateTime;
        
        try {
            localDateTime = LocalDateTime.parse(dateTime, OUTPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        return localDateTime;
    }

    /**
     * Returns a task based on a list of parameters.
     * If the parameters do not match the expected format of a todo command,
     * a StorageCorruptedException containing the full line will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @return A Task object corresponding to the list of parameters.
     * @throws StorageCorruptedException If parameters do not match the format of Todo.
     */
    public static Task parseTodo(String[] parameters) throws StorageCorruptedException {
        checkParamsLength(parameters, 2);

        String isDone = parameters[0].strip();
        String description = parameters[1].strip();

        checkIsDoneValid(parameters, isDone);
        checkDescriptionValid(parameters, description);

        Task task = new Todo(description);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Returns a task based on a list of parameters.
     * If the parameters do not match the expected format of a deadline command,
     * a StorageCorruptedException containing the full line will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @return A Task object corresponding to the list of parameters.
     * @throws StorageCorruptedException If parameters do not match the format of Deadline.
     */
    public static Task parseDeadline(String[] parameters) throws StorageCorruptedException {
        checkParamsLength(parameters, 3);

        String isDone = parameters[0].strip();
        String description = parameters[1].strip();
        String due = parameters[2].strip();

        checkIsDoneValid(parameters, isDone);
        checkDescriptionValid(parameters, description);

        LocalDateTime dueDateTime = parseDateTime(parameters, due);

        Task task = new Deadline(description, dueDateTime);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Returns a task based on a list of parameters.
     * If the parameters do not match the expected format of a deadline command,
     * a StorageCorruptedException containing the full line will be thrown.
     *
     * @param parameters An array of parameters split from a line stored in the save file.
     * @return A Task object corresponding to the list of parameters.
     * @throws StorageCorruptedException If parameters do not match the format of Event.
     */
    public static Task parseEvent(String[] parameters) throws StorageCorruptedException {
        checkParamsLength(parameters, 4);

        String isDone = parameters[0].strip();
        String description = parameters[1].strip();
        String start = parameters[2].strip();
        String end = parameters[3].strip();

        checkIsDoneValid(parameters, isDone);
        checkDescriptionValid(parameters, description);

        LocalDateTime startDateTime = parseDateTime(parameters, start);
        LocalDateTime endDateTime = parseDateTime(parameters, end);

        Task task = new Event(description, startDateTime, endDateTime);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
