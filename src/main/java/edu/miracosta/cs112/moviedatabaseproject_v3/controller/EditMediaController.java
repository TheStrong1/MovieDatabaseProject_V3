package edu.miracosta.cs112.moviedatabaseproject_v3.controller;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.MediaDatabase;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditMediaController {
    @FXML
    private ComboBox<String> mediaTypeCombo;
    @FXML
    private TextField titleField;
    @FXML
    private TextField releaseYearField;
    @FXML
    private TextField durationField;
    @FXML
    private TextField episodesField;
    @FXML
    private TextField ratingField;

    private MediaDatabase mediaDatabase;
    private int editIndex;

    public void setMediaDatabase(MediaDatabase mediaDatabase) {
        this.mediaDatabase = mediaDatabase;
    }

    public void setEditIndex(int editIndex) {
        this.editIndex = editIndex;
    }

    @FXML
    public void editMedia() {
        try {
            String title = titleField.getText();
            int releaseYear = Integer.parseInt(releaseYearField.getText());
            double rating = Double.parseDouble(ratingField.getText());

            if ("Movie".equals(mediaTypeCombo.getValue())) {
                int duration = Integer.parseInt(durationField.getText());
                Movie movie = new Movie(title, releaseYear, rating, duration);
                mediaDatabase.editMedia(editIndex, movie);
            } else {
                int numberOfSeasons = Integer.parseInt(durationField.getText());
                int numberOfEpisodes = Integer.parseInt(episodesField.getText());
                TvShow tvShow = new TvShow(title, releaseYear, rating, numberOfSeasons, numberOfEpisodes);
                mediaDatabase.editMedia(editIndex, tvShow);
            }

            // Close the dialog
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid input. Please enter valid numbers for year, rating, duration, and episodes.");
        } catch (MediaDatabaseException | InvalidTitleException | InvalidYearException | InvalidRatingException |
                 InvalidDurationException | InvalidNumberOfEpisodesException | InvalidNumberOfSeasonsException e) {
            showErrorDialog(e.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}