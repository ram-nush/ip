import java.util.List;
import java.util.Scanner;

public class Bmo {
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private TaskListParser taskListParser;
    
    public Bmo(String filePath) {
        try {
            storage = new Storage(filePath);
            tasks = new TaskList(storage.load());
            StorageParser.checkCorruptedLinesExist(storage);
            
            ui = new Ui();
            taskListParser = new TaskListParser();
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
