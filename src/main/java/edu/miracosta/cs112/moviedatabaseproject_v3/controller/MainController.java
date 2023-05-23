package edu.miracosta.cs112.moviedatabaseproject_v3.controller;

import edu.miracosta.cs112.moviedatabaseproject_v3.Main;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.MediaDatabaseException;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.MediaDatabase;
import edu.miracosta.cs112.moviedatabaseproject_v3.service.SearchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * The MainController class is the controller for the main.fxml file.
 * It handles all user interactions with the main scene.
 * This class is the glue between the view and the model.
 * It is responsible for updating the view when the model changes.
 * It is also responsible for updating the model when the user changes the view.
 *
 * @see Media
 * @see MediaDatabase
 * @see SearchService
 */

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
    private TableColumn<Media, String> durationColumn;

    @FXML
    private TableColumn<Media, String> episodesColumn;

    @FXML
    private TableColumn<Media, Double> ratingColumn;

    private MediaDatabase mediaDatabase;

    private SearchService searchService;

    /**
     * The initialize method is called when the scene is loaded.
     * It is responsible for initializing the scene.
     * It is also responsible for setting up the bindings between the view and the model.
     */

    @FXML
    public void initialize() {
        mediaDatabase = new MediaDatabase();
        searchService = new SearchService();

        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        releaseYearColumn.setCellValueFactory(cellData -> cellData.getValue().releaseYearProperty().asObject());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());

        durationColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Movie movie) {
                return new SimpleObjectProperty<>(String.valueOf(movie.getDuration()));
            } else {
                return new SimpleObjectProperty<>("");
            }
        });

        episodesColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof TvShow tvShow) {
                return new SimpleObjectProperty<>(String.valueOf(tvShow.getNumberOfEpisodes()));
            } else {
                return new SimpleObjectProperty<>("");
            }
        });

        filterCombo.getItems().addAll("All", "Movies", "TV Shows");

        ObservableList<String> sortOptions = FXCollections.observableArrayList("Sort", "Title", "Release Year", "Rating");
        sortCombo.setItems(sortOptions);

        searchField.textProperty().addListener((obs, oldText, newText) -> updateTable());
        filterCombo.valueProperty().addListener((obs, oldText, newText) -> updateTable());
        sortCombo.valueProperty().addListener((obs, oldText, newText) -> updateTable());

        updateTable();
    }

    /**
     * The updateTable method is responsible for updating the table view.
     * It is called whenever the user changes the search field, filter combo box, or sort combo box.
     */

    private void updateTable() {
        String searchText = searchField.getText() == null ? "" : searchField.getText();
        String filterType = filterCombo.getValue();

        List<Media> allMedia;
        if (Objects.equals(filterType, "Movies")) {
            allMedia = mediaDatabase.getAllMovies();
        } else if (Objects.equals(filterType, "TV Shows")) {
            allMedia = mediaDatabase.getAllTvShows();
        } else {
            allMedia = mediaDatabase.getAllMedia();
        }

        List<Media> result = searchService.searchMedia(searchText, allMedia);
        mediaTableView.setItems(FXCollections.observableArrayList(result));
        sortMedia();
    }

    /**
     * The addMedia method is called when the user clicks the add button.
     * It is responsible for opening the add media scene.
     */

    @FXML
    public void addMedia() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddMedia.fxml"));
            Parent addMediaParent = fxmlLoader.load();
            AddMediaController controller = fxmlLoader.getController();
            controller.setMediaDatabase(mediaDatabase);
            Stage stage = new Stage();
            stage.setTitle("Add Media");
            stage.setScene(new Scene(addMediaParent));
            stage.show();
            stage.setOnHidden(event -> updateTable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The handleEditButton method is called when the user clicks the edit button.
     * It is responsible for opening the edit media scene.
     */

    @FXML
    public void handleEditButton() {
        Media selectedMedia = mediaTableView.getSelectionModel().getSelectedItem();

        if (selectedMedia == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a media item to edit.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EditMedia.fxml"));
            Parent editMediaParent = fxmlLoader.load();
            EditMediaController controller = fxmlLoader.getController();
            controller.setMediaDatabase(mediaDatabase);
            controller.setEditIndex(mediaDatabase.getAllMedia().indexOf(selectedMedia));
            Stage stage = new Stage();
            stage.setTitle("Edit Media");
            stage.setScene(new Scene(editMediaParent));
            stage.show();
            stage.setOnHidden(event -> updateTable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The sortMedia method is called when the user clicks the sort button.
     * It is responsible for sorting the media items in the table view.
     */

    @FXML
    public void sortMedia() {
        String sortField = sortCombo.getValue();
        MediaDatabase.SortOption sortOption;

        if (sortField != null) {
            switch (sortField) {
                case "No Sort Option Selected":
                    // Do nothing (no sort)
                case "Title":
                    sortOption = MediaDatabase.SortOption.TITLE_ASCENDING;
                    break;
                case "Release Year":
                    sortOption = MediaDatabase.SortOption.YEAR_ASCENDING;
                    break;
                case "Rating":
                    sortOption = MediaDatabase.SortOption.RATING_ASCENDING;
                    break;
                default:
                    return;
            }

            mediaDatabase.sortMedia(sortOption);
            mediaTableView.setItems(FXCollections.observableArrayList(mediaDatabase.getAllMedia()));
        }
    }

    @FXML
    public void handleRemoveButton() {
        Media selectedMedia = mediaTableView.getSelectionModel().getSelectedItem();

        if (selectedMedia == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a media item to remove.");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this media item?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int index = mediaDatabase.getAllMedia().indexOf(selectedMedia);
                mediaDatabase.removeMedia(index);
                updateTable();
            } catch (MediaDatabaseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * The exitApp method is called when the user clicks the exit button.
     * It is responsible for exiting the application.
     */

    @FXML
    public void exitApp() {
        System.exit(0);
    }
}
