import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
                // invalid task type, throw StorageCorruptedException to Storage
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
        String byString = parameters[2].strip();

        checkIsDoneValid(parameters, isDone);
        checkDescriptionValid(parameters, description);

        LocalDateTime by = parseDateTime(parameters, byString);

        Task task = new Deadline(description, by);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    public static Task parseEvent(String[] parameters) throws StorageCorruptedException {
        checkParamsLength(parameters, 4);

        String isDone = parameters[0].strip();
        String description = parameters[1].strip();
        String fromString = parameters[2].strip();
        String toString = parameters[3].strip();

        checkIsDoneValid(parameters, isDone);
        checkDescriptionValid(parameters, description);

        LocalDateTime from = parseDateTime(parameters, fromString);
        LocalDateTime to = parseDateTime(parameters, toString);

        Task task = new Event(description, from, to);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
