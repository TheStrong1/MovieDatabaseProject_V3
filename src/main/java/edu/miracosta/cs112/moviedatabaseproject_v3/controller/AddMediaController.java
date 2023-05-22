package edu.miracosta.cs112.moviedatabaseproject_v3.controller;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AddMediaController {

    @FXML
    private ComboBox<String> mediaTypeCombo;
    @FXML
    private TextField titleField;
    @FXML
    private TextField releaseYearField;
    @FXML
    private TextField durationField;  // for movies or seasons for TV shows
    @FXML
    private TextField episodesField;  // for TV shows
    @FXML
    private TextField ratingField;

    private MediaDatabase mediaDatabase;

    public void initialize() {
        mediaTypeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    switch (newValue) {
                        case "Movie":
                            durationField.setPromptText("Duration");
                            episodesField.setVisible(false);
                            break;
                        case "TV Show":
                            durationField.setPromptText("Seasons");
                            episodesField.setVisible(true);
                            break;
                    }
                }
            }
        });
    }

    public void setMediaDatabase(MediaDatabase mediaDatabase) {
        this.mediaDatabase = mediaDatabase;
    }

    public void addMedia() {
        String title = titleField.getText().trim();
        String mediaType = mediaTypeCombo.getValue();

        if (mediaType == null) {
            showErrorAlert(new Exception("Must select either Movie or TV Show."));
            return;
        }

        if (title == null || title.isEmpty()) {
            showErrorAlert(new Exception("Title cannot be empty."));
            return;
        }

        try {
            int releaseYear = Integer.parseInt(releaseYearField.getText());
            double rating = Double.parseDouble(ratingField.getText());

            if (mediaType.equals("Movie")) {
                int duration = Integer.parseInt(durationField.getText());
                Movie movie = new Movie(title, releaseYear, rating, duration);
                mediaDatabase.addMedia(movie);
                showSuccessAlert(movie);
            } else if (mediaType.equals("TV Show")) {
                int numberOfSeasons = Integer.parseInt(durationField.getText());
                int numberOfEpisodes = Integer.parseInt(episodesField.getText());
                TvShow tvShow = new TvShow(title, releaseYear, rating, numberOfEpisodes, numberOfSeasons);
                mediaDatabase.addMedia(tvShow);
                showSuccessAlert(tvShow);
            }
        } catch (NumberFormatException e) {
            showErrorAlert(new Exception("Invalid input. Please ensure that all fields are correctly filled."));
        } catch (InvalidTitleException | InvalidYearException | InvalidRatingException | InvalidDurationException | InvalidNumberOfEpisodesException | InvalidNumberOfSeasonsException | MediaDatabaseException e) {
            showErrorAlert(e);
        }
    }

    private void showSuccessAlert(Media media) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(media.getTitle() + " was successfully added to the database.");
        alert.showAndWait();
    }

    private void showErrorAlert(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Failed to add media: " + e.getMessage());
        alert.showAndWait();
    }
}