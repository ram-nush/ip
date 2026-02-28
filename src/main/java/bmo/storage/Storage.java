package bmo.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import bmo.exception.BmoException;
import bmo.exception.StorageCorruptedException;
import bmo.task.Task;

/**
 * Represents the storage object of the program. A <code>Storage</code> object
 * corresponds to a file path where the save file is saved e.g., <code>data/bmo.txt</code>
 * and stores lines read from the save file which do not match the format e.g.,
 * <code>T | x | read | book</code>
 */
public class Storage {

    private final Path path;
    private List<String> corruptedLines;

    /**
     * Initializes a <code>Storage</code> object which takes in a file path
     * representing the location of the save file.
     * An InvalidPathException may be thrown if the file path does not match a valid path.
     *
     * @param filePath The string which corresponds to the file path where the tasks are stored.
     * @throws InvalidPathException If the path cannot be converted to a <code>Path</code>
     */
    public Storage(String filePath) throws InvalidPathException {
        this.path = Paths.get(filePath);
        this.corruptedLines = new ArrayList<String>();
    }

    /**
     * Returns whether there are corrupted lines read by the storage object.
     *
     * @return true if there are corrupted lines, false otherwise.
     */
    public boolean hasCorruptedLines() {
        return !this.corruptedLines.isEmpty();
    }

    public List<String> getCorruptedLines() {
        return this.corruptedLines;
    }

    /**
     * Loads the save file if it exists.
     * Reads through the lines and creates a list of tasks.
     * Returns the list of tasks.
     * If there are errors reading the save file, a BmoException will be thrown.
     * If there are corrupted lines, a StorageCorruptedException will be thrown.
     *
     * @return List of tasks read from save file.
     * @throws BmoException If save file cannot be read.
     * @throws StorageCorruptedException If there exist corrupted lines in the save file.
     */
    public List<Task> load() throws BmoException {
        // Create list to store lines from save file
        List<String> lines;
        // Create list to store tasks created from lines
        List<Task> tasks = new ArrayList<Task>();

        try {
            // Read lines from save file
            lines = Files.readAllLines(this.path);
        } catch (IOException e) {
            // Reading from save file causes errors
            String message = String.format(BmoException.BMO_READ_FILE_MESSAGE, this.path);
            String suggestion = BmoException.BMO_FILE_SUGGESTION_EXIST;
            throw new BmoException(message, suggestion);
        }

        for (String line : lines) {
            try {
                // Create and add Task object to list of tasks
                Task task = StorageParser.parseLine(line);
                tasks.add(task);
            } catch (StorageCorruptedException e) {
                // Parsing a corrupted line can throw StorageCorruptedException
                // Catch and save corrupted line to storage
                this.corruptedLines.add(line);
            }
        }

        return tasks;
    }

    /**
     * Writes the given lines to the save file, overwriting existing text.
     * If there are errors writing to the save file, a BmoException will be thrown.
     * If there are corrupted lines, they will not be written and become lost.
     *
     * @param saveText String of all tasks.
     * @throws BmoException If save file cannot be read.
     */
    public void save(String saveText) throws BmoException {
        try {
            // Create directories if they do not exist
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            // Create file if it does not exist
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            // Opening save file causes errors
            String message = String.format(BmoException.BMO_WRITE_FILE_MESSAGE, this.path);
            String suggestion = BmoException.BMO_FILE_SUGGESTION_EXIST;
            throw new BmoException(message, suggestion);
        }

        try {
            // Write to save file
            Files.writeString(path, saveText);
        } catch (IOException e) {
            // Writing to save file causes errors
            String message = String.format(BmoException.BMO_WRITE_FILE_MESSAGE, this.path);
            String suggestion = BmoException.BMO_WRITE_FILE_SUGGESTION_VIEW;
            throw new BmoException(message, suggestion);
        }
    }
}
