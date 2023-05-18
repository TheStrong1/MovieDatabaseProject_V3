package edu.miracosta.cs112.moviedatabaseproject_v3.controller;

import edu.miracosta.cs112.moviedatabaseproject_v3.Main;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.MediaDatabase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterCombo;

    @FXML
    private ComboBox<String> sortCombo;

    @FXML
    private TableView<Media> mediaTableView;

    @FXML
    private TableColumn<Media, String> titleColumn;

    @FXML
    private TableColumn<Media, Integer> releaseYearColumn;

    @FXML
    private TableColumn<Media, Integer> durationColumn;

    @FXML
    private TableColumn<Media, Integer> episodesColumn;

    @FXML
    private TableColumn<Media, Double> ratingColumn;

    private MediaDatabase mediaDatabase;

    @FXML
    public void initialize() {
        // Initialize the mediaDatabase
        mediaDatabase = new MediaDatabase();

        // Set up the table columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        releaseYearColumn.setCellValueFactory(cellData -> cellData.getValue().releaseYearProperty().asObject());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());

        // Set up the cell factories for durationColumn and episodesColumn
        durationColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Movie movie) {
                return movie.durationProperty().asObject();
            } else {
                return new SimpleIntegerProperty(-1).asObject();
            }
        });

        episodesColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof TvShow tvShow) {
                return tvShow.numberOfEpisodesProperty().asObject();
            } else {
                return new SimpleIntegerProperty(-1).asObject();
            }
        });

        // For now, filterCombo just contains two options
        filterCombo.getItems().addAll("All", "Movies", "TV Shows");

        // Initialize the sortCombo
        ObservableList<String> sortOptions = FXCollections.observableArrayList("Title", "Release Year", "Rating");
        sortCombo.setItems(sortOptions);

        // Update the table when the search text or filter option changes
        searchField.textProperty().addListener((obs, oldText, newText) -> updateTable());
        filterCombo.valueProperty().addListener((obs, oldText, newText) -> updateTable());
        sortCombo.valueProperty().addListener((obs, oldText, newText) -> sortMedia());

        // Load the initial data
        updateTable();
    }

    private void updateTable() {
        String searchText = searchField.getText();
        String filter = filterCombo.getValue();

        if (Objects.equals(filter, "All")) {
            mediaTableView.setItems(FXCollections.observableArrayList(mediaDatabase.getAllMedia(searchText)));
        } else if (Objects.equals(filter, "Movies")) {
            mediaTableView.setItems(FXCollections.observableArrayList(mediaDatabase.getAllMovies(searchText)));
        } else if (Objects.equals(filter, "TV Shows")) {
            mediaTableView.setItems(FXCollections.observableArrayList(mediaDatabase.getAllTvShows(searchText)));
        }

        // Ensure the table is sorted after updating it
        sortMedia();
    }

    @FXML
    public void addMedia() {
        try {
            // Load the fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddMedia.fxml"));
            Parent addMediaParent = fxmlLoader.load();

            // Access the controller and call a method
            AddMediaController controller = fxmlLoader.getController();

            // Pass the MediaDatabase to the AddMediaController
            controller.setMediaDatabase(mediaDatabase);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setTitle("Add Media");
            stage.setScene(new Scene(addMediaParent));
            stage.show();

            // Refresh the table view when the AddMedia stage is closed
            stage.setOnHidden(event -> updateTable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditButton() {
        Media selectedMedia = mediaTableView.getSelectionModel().getSelectedItem();

        if (selectedMedia == null) {
            // Show an alert if no media is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a media item to edit.");
            alert.showAndWait();
            return;
        }

        try {
            // Load the fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EditMedia.fxml"));
            Parent editMediaParent = fxmlLoader.load();

            // Access the controller and call a method
            EditMediaController controller = fxmlLoader.getController();

            // Pass the MediaDatabase to the EditMediaController
            controller.setMediaDatabase(mediaDatabase);

            // Pass the index of the selected media to the EditMediaController
            controller.setEditIndex(mediaDatabase.getAllMedia("").indexOf(selectedMedia));

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setTitle("Edit Media");
            stage.setScene(new Scene(editMediaParent));
            stage.show();

            // Refresh the table view when the EditMedia stage is closed
            stage.setOnHidden(event -> updateTable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sortMedia() {
        String sortField = sortCombo.getValue();

        if (sortField != null) {
            switch (sortField) {
                case "Title":
                    FXCollections.sort(mediaTableView.getItems(), Comparator.comparing(Media::getTitle));
                    break;
                case "Release Year":
                    FXCollections.sort(mediaTableView.getItems(), Comparator.comparingInt(Media::getReleaseYear));
                    break;
                case "Rating":
                    FXCollections.sort(mediaTableView.getItems(), Comparator.comparingDouble(Media::getRating));
                    break;
                default:
                    // Handle invalid sort field if necessary
                    break;
            }
        }
    }

    @FXML
    public void exitApp() {
        System.exit(0);
    }
}
