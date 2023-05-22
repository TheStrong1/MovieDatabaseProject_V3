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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private TableColumn<Media, String> durationColumn;

    @FXML
    private TableColumn<Media, String> episodesColumn;

    @FXML
    private TableColumn<Media, Double> ratingColumn;


    private SearchService searchService;

    private static final String JSON_FILE_PATH = "Media.json";
    private MediaDatabase mediaDatabase;

    @FXML
    public void initialize() {
        searchService = new SearchService();
        try {
            mediaDatabase = new MediaDatabase(JSON_FILE_PATH);
            initAfterMediaDatabase();
        } catch (MediaDatabaseException e) {
            // You could use a dialog to alert the user to the problem here
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while loading the data.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Setter method for the MediaDatabase
    public void setMediaDatabase(MediaDatabase mediaDatabase) {
        this.mediaDatabase = mediaDatabase;
        initAfterMediaDatabase();
    }

    private void initAfterMediaDatabase() {
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

        ObservableList<String> sortOptions = FXCollections.observableArrayList("Title", "Release Year", "Rating");
        sortCombo.setItems(sortOptions);

        searchField.textProperty().addListener((obs, oldText, newText) -> updateTable());
        filterCombo.valueProperty().addListener((obs, oldText, newText) -> updateTable());
        sortCombo.valueProperty().addListener((obs, oldText, newText) -> updateTable());

        updateTable();
    }

    private void updateTable() {
        String searchText = searchField.getText() == null ? "" : searchField.getText();
        String filterType = filterCombo.getValue();

        List<Media> allMedia;
        if (Objects.equals(filterType, "Movies")) {
            List<Movie> allMovies = mediaDatabase.getAllMovies();
            allMedia = new ArrayList<>();
            allMedia.addAll(allMovies);
        } else if (Objects.equals(filterType, "TV Shows")) {
            List<TvShow> allTvShows = mediaDatabase.getAllTvShows();
            allMedia = new ArrayList<>();
            allMedia.addAll(allTvShows);
        } else {
            allMedia = mediaDatabase.getAllMedia();
        }

        List<Media> result = searchService.searchMedia(searchText, allMedia);
        mediaTableView.setItems(FXCollections.observableArrayList(result));
        sortMedia();
    }

    @FXML
    public void addMedia() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/edu/miracosta/cs112/moviedatabaseproject_v3/AddMedia.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/edu/miracosta/cs112/moviedatabaseproject_v3/EditMedia.fxml"));
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

    @FXML
    public void sortMedia() {
        String sortField = sortCombo.getValue();
        MediaDatabase.SortOption sortOption;

        if (sortField != null) {
            switch (sortField) {
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
    public void exitApp() throws MediaDatabaseException {
        mediaDatabase.saveToJson();
        System.exit(0);
    }
}
