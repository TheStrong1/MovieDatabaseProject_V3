package edu.miracosta.cs112.moviedatabaseproject_v3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class is the entry point for the Media Database application.
 * This class extends the Application class from JavaFX and overrides the start method.
 *
 * @author Chris
 * @version UD_3
 */
public class Main extends Application {

    /**
     * The start method is the main entry point for any JavaFX application.
     * It creates the main application window and loads the initial scene.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set. The primary stage is
     *              created by the platform.
     * @throws IOException if there is an error loading the fxml file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the main fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        // Create the scene with the loaded fxml file and size 600x400
        Scene scene = new Scene(fxmlLoader.load() , 600, 400);
        // Set the title of the application window
        stage.setTitle("Media Database");
        // Set the scene for the stage
        stage.setScene(scene);
        // Display the stage
        stage.show();
    }

    /**
     * The main method is the entry point for the JVM.
     * It launches the JavaFX application.
     *
     * @param args command-line arguments. Not used in this application.
     */
    public static void main(String[] args) {
        launch();
    }
}
