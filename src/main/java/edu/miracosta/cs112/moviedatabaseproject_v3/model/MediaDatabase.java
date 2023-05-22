
package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.MediaDatabaseException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidRatingException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidYearException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for maintaining a list of Media items.
 * It provides functionalities to add, remove, edit, sort and fetch Media items.
 * It can also load and save Media items from and to a JSON file.
 */
public class MediaDatabase {
    private final List<Media> media;
    private final String jsonFilePath;

    public MediaDatabase(String jsonFilePath) throws MediaDatabaseException {
        this.media = new ArrayList<>();
        this.jsonFilePath = jsonFilePath;
        loadFromJson();
    }

    public void addMedia(Media m) throws MediaDatabaseException, InvalidRatingException, InvalidYearException {
        if (m == null) {
            throw new MediaDatabaseException("Failed to add media. Media object can't be null.");
        }
        if (media.contains(m)) {
            throw new MediaDatabaseException("Media item is already in the database: " + m.getTitle());
        }
        validateMedia(m);
        media.add(m);
        saveToJson();
    }

    public void removeMedia(int index) throws MediaDatabaseException {
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Failed to remove media. Invalid index " + index + ". Index must be within the range of the media list.");
        }
        media.remove(index);
        saveToJson();
    }

    public void editMedia(int index, Media m) throws MediaDatabaseException, InvalidRatingException, InvalidYearException {
        if (m == null) {
            throw new MediaDatabaseException("Failed to edit media. Media object can't be null.");
        }
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Failed to edit media. Invalid index " + index + ". Index must be within the range of the media list.");
        }
        validateMedia(m);
        media.set(index, m);
        saveToJson();
    }

    private void loadFromJson() throws MediaDatabaseException {
        try {
            List<Media> loadedMedia = JsonUtil.readMediaFromJson(jsonFilePath);
            media.clear();
            media.addAll(loadedMedia);
        } catch (IOException e) {
            throw new MediaDatabaseException("Failed to load data from JSON: " + e.getMessage(), e);
        }
    }

    public void saveToJson() throws MediaDatabaseException {
        try {
            JsonUtil.writeMediaToJson(media, jsonFilePath);
        } catch (IOException e) {
            throw new MediaDatabaseException("Failed to save data to JSON: " + e.getMessage(), e);
        }
    }

    private void validateMedia(Media m) throws InvalidRatingException, InvalidYearException {
        if (m.getReleaseYear() < 1888 || m.getReleaseYear() > java.time.Year.now().getValue()) {
            throw new InvalidYearException("Invalid release year: " + m.getReleaseYear());
        }
        if (m.getRating() < 0.0 || m.getRating() > 10.0) {
            throw new InvalidRatingException("Invalid rating: " + m.getRating());
        }
    }

    public List<Media> getAllMedia() {
        return new ArrayList<>(media);
    }

    public List<Movie> getAllMovies() {
        return media.stream()
                .filter(m -> m instanceof Movie)
                .map(m -> (Movie) m)
                .collect(Collectors.toList());
    }

    public List<TvShow> getAllTvShows() {
        return media.stream()
                .filter(m -> m instanceof TvShow)
                .map(m -> (TvShow) m)
                .collect(Collectors.toList());
    }

    public void sortMedia(SortOption sortOption) {
        switch (sortOption) {
            case DURATION_ASCENDING:
                media.sort(Comparator.comparingInt(m -> m instanceof Movie ? ((Movie) m).getDuration() : Integer.MAX_VALUE));
                break;
            case DURATION_DESCENDING:
                media.sort(Comparator.comparingInt((Media m) -> m instanceof Movie ? ((Movie) m).getDuration() : Integer.MAX_VALUE).reversed());
                break;
            case SEASONS_ASCENDING:
                media.sort(Comparator.comparingInt(m -> m instanceof TvShow ? ((TvShow) m).getNumberOfSeasons() : Integer.MAX_VALUE));
                break;
            case SEASONS_DESCENDING:
                media.sort(Comparator.comparingInt((Media m) -> m instanceof TvShow ? ((TvShow) m).getNumberOfSeasons() : Integer.MAX_VALUE).reversed());
                break;
            case EPISODES_ASCENDING:
                media.sort(Comparator.comparingInt(m -> m instanceof TvShow ? ((TvShow) m).getNumberOfEpisodes() : Integer.MAX_VALUE));
                break;
            case EPISODES_DESCENDING:
                media.sort(Comparator.comparingInt((Media m) -> m instanceof TvShow ? ((TvShow) m).getNumberOfEpisodes() : Integer.MAX_VALUE).reversed());
                break;
            default:
                Comparator<Media> comparator = switch (sortOption) {
                    case TITLE_ASCENDING -> Comparator.comparing(Media::getTitle);
                    case TITLE_DESCENDING -> Comparator.comparing(Media::getTitle).reversed();
                    case YEAR_ASCENDING -> Comparator.comparingInt(Media::getReleaseYear);
                    case YEAR_DESCENDING -> Comparator.comparingInt(Media::getReleaseYear).reversed();
                    case RATING_ASCENDING -> Comparator.comparingDouble(Media::getRating);
                    case RATING_DESCENDING -> Comparator.comparingDouble(Media::getRating).reversed();
                    default -> throw new IllegalArgumentException("Invalid sort option");
                };
                media.sort(comparator);
        }
    }

    public enum SortOption {
        TITLE_ASCENDING,
        TITLE_DESCENDING,
        YEAR_ASCENDING,
        YEAR_DESCENDING,
        RATING_ASCENDING,
        RATING_DESCENDING,
        DURATION_ASCENDING,
        DURATION_DESCENDING,
        SEASONS_ASCENDING,
        SEASONS_DESCENDING,
        EPISODES_ASCENDING,
        EPISODES_DESCENDING
    }
}