package edu.miracosta.cs112.moviedatabaseproject_v3;

import edu.miracosta.cs112.moviedatabaseproject_v3.controller.MainController;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.MediaDatabaseException;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.MediaDatabase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class Main extends Application {

    private static final String JSON_FILE_PATH = "/Users/admin3/IdeaProjects/MovieDatabaseProject_V3/src/main/resources/edu/miracosta/cs112/moviedatabaseproject_v3/Media.json";

    private MediaDatabase mediaDatabase;

    @Override
    public void start(Stage stage) throws MediaDatabaseException {
        // Load data from JSON file into mediaDatabase
        mediaDatabase = new MediaDatabase(JSON_FILE_PATH);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            // Get the controller and set mediaDatabase to it
            MainController mainController = fxmlLoader.getController();
            mainController.setMediaDatabase(mediaDatabase);

            stage.setTitle("Media Database");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while loading the application.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }
    }

    @Override
    public void stop() {
        // Save changes to mediaDatabase here
        try {
            mediaDatabase.saveToJson();
        } catch (MediaDatabaseException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while saving the data.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
