package bmo.javafx;

import bmo.Bmo;
import bmo.exception.BmoException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bmo bmo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image bmoImage = new Image(this.getClass().getResourceAsStream("/images/bmo.png"));

    @FXML
    public void initialize() {        
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setBmo(Bmo b) {
        bmo = b;

        String loadingMessage = bmo.getLoadingErrors();
        String welcomeMessage = bmo.getWelcomeMessage();
        String commandFormats = bmo.getCommandFormats();
        
        if (!loadingMessage.isEmpty()) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getBmoDialog(loadingMessage, bmoImage)
            );
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getBmoDialog(welcomeMessage, bmoImage),
                DialogBox.getBmoDialog(commandFormats, bmoImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Bmo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        
        try {
            String response = bmo.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getBmoDialog(response, bmoImage)
            );

            if (bmo.isExitInput(input)) {
                String closingMessage = bmo.getClosingMessage();
                dialogContainer.getChildren().addAll(
                        DialogBox.getBmoDialog(closingMessage, bmoImage)
                );
                
                // Create delay timer
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                
                // Link it to closing the application
                delay.setOnFinished(e -> Platform.exit());
                
                // Start the timer
                delay.play();
            }
        } catch (BmoException e) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getBmoDialog(e.toString(), bmoImage)
            );
        } finally {
            userInput.clear();
        }
    }
}