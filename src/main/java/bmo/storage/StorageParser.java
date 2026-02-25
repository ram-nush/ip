package bmo.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

    public static void checkCorruptedLinesExist(Storage storage) throws StorageCorruptedException {
        if (storage.hasCorruptedLines()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINES_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINES_SUGGESTION;
            List<String> corruptedLines = storage.getCorruptedLines();
            throw new StorageCorruptedException(message, suggestion, corruptedLines);
        }
    }
    
    public static void checkParamsLength(String[] parameters, int expectedLength) throws StorageCorruptedException {
        if (parameters.length != expectedLength) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
    }

    public static void checkIsDoneValid(String[] parameters, String isDone) throws StorageCorruptedException {
        if (!isDone.equals("0") && !isDone.equals("1")) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
    }

    public static void checkDescriptionValid(String[] parameters, String description) throws StorageCorruptedException {
        if (description.isEmpty()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(parameters);
            throw new StorageCorruptedException(message, suggestion, list);
        }
    }
    
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
