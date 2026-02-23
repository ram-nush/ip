import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    
    public static String INPUT_DATETIME_PATTERN = "dd-MM-yyyy HHmm";
    public static String OUTPUT_DATETIME_PATTERN = "MMM d yyyy HHmm";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATETIME_PATTERN);
    public static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATETIME_PATTERN);
    
    private final Path path;
    private List<String> corruptedLines;
    
    public Storage(String filePath) throws InvalidPathException {
        this.path = Paths.get(filePath);
        this.corruptedLines = new ArrayList<String>();
    }
    
    public boolean hasCorruptedLines() {
        return !this.corruptedLines.isEmpty();
    }
    
    public List<String> getCorruptedLines() {
        return this.corruptedLines;
    }
    
    public List<Task> load() throws BmoException {
        
        List<String> lines;
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            lines = Files.readAllLines(this.path);
        } catch (IOException e) {
            String message = String.format(BmoException.BMO_READ_FILE_MESSAGE, this.path);
            String suggestion = BmoException.BMO_FILE_SUGGESTION_EXIST;
            throw new BmoException(message, suggestion);
        }
        
        for (String line : lines) {
            String[] properties = line.split(" \\| ");
            String type = properties[0];
            Task task;
            
            try {
                switch (type) {
                case "T":
                    task = parseTodo(properties);
                    tasks.add(task);
                    break;

                case "D":
                    task = parseDeadline(properties);
                    tasks.add(task);
                    break;

                case "E":
                    task = parseEvent(properties);
                    tasks.add(task);
                    break;

                default:
                    // invalid task, store for later
                    // need to inform user that these lines are corrupted
                    // no need to throw error, otherwise other valid tasks are lost
                    this.corruptedLines.add(line);
                    break;
                }
            } catch (StorageCorruptedException e) {
                this.corruptedLines.add(line);
            }
        }
        
        return tasks;
    }
    
    public Task parseTodo(String[] properties) throws StorageCorruptedException {
        if (properties.length != 3) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }

        String isDone = properties[1].strip();
        String description = properties[2].strip();

        if (!isDone.equals("0") && !isDone.equals("1")) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }

        if (description.isEmpty()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        Task task = new Todo(description);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
    
    public Task parseDeadline(String[] properties) throws StorageCorruptedException {
        if (properties.length != 4) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        String isDone = properties[1].strip();
        String description = properties[2].strip();
        String byString = properties[3].strip();

        if (!isDone.equals("0") && !isDone.equals("1")) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }

        if (description.isEmpty()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        LocalDateTime by;
        
        try {
            by = LocalDateTime.parse(byString, outputFormatter);
        } catch (DateTimeParseException e) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        Task task = new Deadline(description, by);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
    
    public Task parseEvent(String[] properties) throws StorageCorruptedException {
        if (properties.length != 5) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        String isDone = properties[1].strip();
        String description = properties[2].strip();
        String fromString = properties[3].strip();
        String toString = properties[4].strip();

        if (!isDone.equals("0") && !isDone.equals("1")) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }

        if (description.isEmpty()) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }

        LocalDateTime from;
        LocalDateTime to;

        try {
            from = LocalDateTime.parse(fromString, outputFormatter);
            to = LocalDateTime.parse(toString, outputFormatter);
        } catch (DateTimeParseException e) {
            String message = StorageCorruptedException.BMO_CORRUPTED_LINE_MESSAGE;
            String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINE_SUGGESTION;
            List<String> list = Arrays.asList(properties);
            throw new StorageCorruptedException(message, suggestion, list);
        }
        
        Task task = new Event(description, from, to);
        if (isDone.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
    
    public void save(String saveText) throws BmoException {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            String message = String.format(BmoException.BMO_WRITE_FILE_MESSAGE, this.path);
            String suggestion = BmoException.BMO_FILE_SUGGESTION_EXIST;
            throw new BmoException(message, suggestion);
        }

        try {
            Files.writeString(path, saveText);
        } catch (IOException e) {
            String message = String.format(BmoException.BMO_WRITE_FILE_MESSAGE, this.path);
            String suggestion = BmoException.BMO_WRITE_FILE_SUGGESTION_VIEW;
            throw new BmoException(message, suggestion);
        }
    }
}
