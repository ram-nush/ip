package bmo;

import java.util.Scanner;

import bmo.command.Command;
import bmo.exception.BmoException;
import bmo.exception.StorageCorruptedException;
import bmo.parser.TaskListParser;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.storage.StorageParser;
import bmo.ui.Ui;

public class Bmo {
    
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private TaskListParser taskListParser;
    
    public Bmo(String filePath) {
        try {
            // Initialize components
            storage = new Storage(filePath);
            ui = new Ui();
            taskListParser = new TaskListParser();
            
            // Load lines from storage into tasks
            taskList = new TaskList(storage.load());
            
            // Determine if there exist corrupted lines stored
            StorageParser.checkCorruptedLinesExist(storage);
        } catch (StorageCorruptedException e) {
            // Corrupted lines exist, display error message to user
            // taskList will contain tasks from non-corrupted lines
            ui.showErrorMessage(e);
        } catch (BmoException e) {
            // Cannot read from file
            ui.showErrorMessage(e);
            
            // Create empty task list
            taskList = new TaskList();
        }
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        // Display retrieved tasks to user
        String retrieveText = taskList.toString();
        ui.showRetrieveMessage(retrieveText);
        
        // Display welcome message to user
        ui.showWelcomeMessage();
        
        boolean isExit = false;
        while (!isExit) {
            // Previous command was not the final command
            try {
                // Read user input
                String userInput = scanner.nextLine();
                
                // Parse user input to create a Command object
                Command command = taskListParser.parseCommand(userInput, ui, taskList, storage);
                
                // Execute the command on the other components
                command.execute(taskList, ui, storage);
                
                // Determine whether this is a final command
                isExit = command.isExit();
            } catch (BmoException e) {
                ui.showErrorMessage(e);
            }
        }
        
        // Display closing message to user
        ui.showByeMessage();
        scanner.close();
    }
    
    public static void main(String[] args) {
        String BMO_FILE_PATH = "data/bmo.txt";
        new Bmo(BMO_FILE_PATH).run();
    }
}
