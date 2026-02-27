package bmo.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import bmo.Bmo;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private static final String BMO_FILE_PATH = "data/bmo.txt";

    private Bmo bmo = new Bmo(BMO_FILE_PATH);
    
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBmo(bmo);  // inject the Bmo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
