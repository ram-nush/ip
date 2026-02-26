package bmo.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image bmoImage = new Image(this.getClass().getResourceAsStream("/images/bmo.png"));
    
    private static final String BMO_FILE_PATH = "data/bmo.txt";

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    // Existing constructor
    public Main(String filePath) {
        
    }
    
    // Overloaded constructor
    public Main() {
        this(BMO_FILE_PATH);
    }
    
    @Override
    public void start(Stage stage) {
        //Setting up required components

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        DialogBox dialogBox = new DialogBox("Hello!", userImage);
        dialogContainer.getChildren().addAll(dialogBox);

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //More code to be added here later
    }
}
