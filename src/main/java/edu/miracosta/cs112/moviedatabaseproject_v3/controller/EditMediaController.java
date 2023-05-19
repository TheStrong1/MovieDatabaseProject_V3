package edu.miracosta.cs112.moviedatabaseproject_v3.controller;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
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

    public void initialize() {
        mediaTypeCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            episodesField.setVisible("TV Show".equals(newValue));
        });
    }

    public void setMediaDatabase(MediaDatabase mediaDatabase) {
        if (mediaDatabase == null) {
            throw new IllegalArgumentException("MediaDatabase cannot be null.");
        }
        this.mediaDatabase = mediaDatabase;
    }

    public void setEditIndex(int editIndex) {
        if (editIndex < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
        this.editIndex = editIndex;

        Media media = mediaDatabase.getAllMedia(null).get(editIndex);

        titleField.setText(media.getTitle());
        releaseYearField.setText(String.valueOf(media.getReleaseYear()));
        ratingField.setText(String.valueOf(media.getRating()));

        if (media instanceof Movie) {
            mediaTypeCombo.setValue("Movie");
            durationField.setText(String.valueOf(((Movie) media).getDuration()));
        } else if (media instanceof TvShow) {
            mediaTypeCombo.setValue("TV Show");
            durationField.setText(String.valueOf(((TvShow) media).getNumberOfSeasons()));
            episodesField.setText(String.valueOf(((TvShow) media).getNumberOfEpisodes()));
        }
    }

    @FXML
    public void editMedia() {
        String title = titleField.getText();
        String releaseYearText = releaseYearField.getText();
        String ratingText = ratingField.getText();
        String durationText = durationField.getText();
        String episodesText = episodesField.getText();

        if (title.isEmpty() || releaseYearText.isEmpty() || ratingText.isEmpty() || durationText.isEmpty() ||
                ("TV Show".equals(mediaTypeCombo.getValue()) && episodesText.isEmpty())) {
            showErrorDialog("All fields must be filled.");
            return;
        }

        try {
            int releaseYear = Integer.parseInt(releaseYearText);
            double rating = Double.parseDouble(ratingText);

            if ("Movie".equals(mediaTypeCombo.getValue())) {
                int duration = Integer.parseInt(durationText);
                Movie movie = new Movie(title, releaseYear, rating, duration);
                mediaDatabase.editMedia(editIndex, movie);
            } else {
                int numberOfSeasons = Integer.parseInt(durationText);
                int numberOfEpisodes = Integer.parseInt(episodesText);
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
