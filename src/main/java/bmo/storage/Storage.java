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

public class Storage {
    
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
                // No need to throw error, otherwise other valid tasks are lost
                // Catch and save corrupted line to storage
                this.corruptedLines.add(line);
            }
            
        }
        
        return tasks;
    }
    
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
