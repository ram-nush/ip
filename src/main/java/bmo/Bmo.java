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
    private TaskList tasks;
    private Ui ui;
    private TaskListParser taskListParser;
    
    public Bmo(String filePath) {
        try {
            ui = new Ui();
            storage = new Storage(filePath);
            taskListParser = new TaskListParser();
            tasks = new TaskList(storage.load());
            
            StorageParser.checkCorruptedLinesExist(storage);
        } catch (StorageCorruptedException e) {
            ui.showErrorMessage(e);
        } catch (BmoException e) {
            ui.showErrorMessage(e);
            tasks = new TaskList();
        }
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        String retrieveText = tasks.toString();
        ui.showRetrieveMessage(retrieveText);
        ui.showWelcomeMessage();
        
        boolean isExit = false;
        while (!isExit) {
            try {
                String userInput = scanner.nextLine();
                Command command = taskListParser.parseCommand(userInput, ui, tasks, storage);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (BmoException e) {
                ui.showErrorMessage(e);
            }
        }
        ui.showByeMessage();
        scanner.close();
    }
    
    public static void main(String[] args) {
        String BMO_FILE_PATH = "data/bmo.txt";
        new Bmo(BMO_FILE_PATH).run();
    }
}
