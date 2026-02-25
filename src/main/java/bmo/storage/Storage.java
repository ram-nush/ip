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
            try {
                // may cause bmo.exception.StorageCorruptedException
                // no need to throw error, otherwise other valid tasks are lost
                // catch and save corrupted line
                Task task = StorageParser.parseLine(line);
                tasks.add(task);
            } catch (StorageCorruptedException e) {
                this.corruptedLines.add(line);
            }
            
        }
        
        return tasks;
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
