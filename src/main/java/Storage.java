import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    
    public static String INPUT_DATETIME_PATTERN = "dd-MM-yyyy HHmm";
    public static String OUTPUT_DATETIME_PATTERN = "MMM d yyyy HHmm";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATETIME_PATTERN);
    public static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATETIME_PATTERN);
    
    private final Path path;
    
    public Storage(String filePath) throws InvalidPathException {
        this.path = Paths.get(filePath);
    }
    
    public List<Task> load() throws IOException, ArrayIndexOutOfBoundsException, 
            DateTimeParseException, MissingArgumentException {
        if (Files.notExists(this.path)) {
            throw new IOException("File does not exist at " + this.path);
        }
        List<String> lines = Files.readAllLines(this.path);
        List<Task> tasks = new ArrayList<Task>();
        
        for (String line : lines) {
            String[] properties = line.split(" \\| ");
            String type = properties[0];
            Task task;
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
                break;
            }
        }
        return tasks;
    }
    
    public Task parseTodo(String[] properties) throws ArrayIndexOutOfBoundsException, 
            MissingArgumentException {
        if (properties.length >= 3) {
            String isDone = properties[1];
            String description = properties[2];
            Task task = new Todo(description);
            if (isDone.equals("1")) {
                task.markAsDone();
            }
            return task;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public Task parseDeadline(String[] properties) throws ArrayIndexOutOfBoundsException, 
            MissingArgumentException, DateTimeParseException {
        if (properties.length >= 4) {
            String isDone = properties[1];
            String description = properties[2];
            String byString = properties[3];
            LocalDateTime by = LocalDateTime.parse(byString, outputFormatter);
            Task task = new Deadline(description, by);
            if (isDone.equals("1")) {
                task.markAsDone();
            }
            return task;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public Task parseEvent(String[] properties) throws ArrayIndexOutOfBoundsException, 
            MissingArgumentException, DateTimeParseException {
        if (properties.length >= 5) {
            String isDone = properties[1];
            String description = properties[2];
            String fromString = properties[3];
            String toString = properties[4];
            LocalDateTime from = LocalDateTime.parse(fromString, outputFormatter);
            LocalDateTime to = LocalDateTime.parse(toString, outputFormatter);
            Task task = new Event(description, from, to);
            if (isDone.equals("1")) {
                task.markAsDone();
            }
            return task;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void save(String saveText) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        if (Files.notExists(path)) {
            Files.createFile(path);
        }

        Files.writeString(path, saveText);
    }
}
