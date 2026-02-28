package bmo.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import bmo.Bmo;

/**
 * A GUI for Bmo using FXML.
 */
public class Main extends Application {

    private static final String BMO_FILE_PATH = "data/bmo.txt";

    private Bmo bmo = new Bmo(BMO_FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            stage.setTitle("BMO");

            Image bmoIcon = new Image(this.getClass().getResourceAsStream("/images/bmoIcon.png"));
            stage.getIcons().add(bmoIcon);

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setBmo(bmo);  // inject the Bmo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
