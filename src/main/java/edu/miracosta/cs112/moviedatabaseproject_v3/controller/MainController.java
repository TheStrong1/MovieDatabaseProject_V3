package edu.miracosta.cs112.moviedatabaseproject_v3.controller;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    }

    @FXML
    public void addMedia() {
        try {
            // Load the fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddMedia.fxml"));
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
    public void sortMedia() {
        // We'll implement this later
    }

    @FXML
    public void exitApp() {
        System.exit(0);
    }
}
