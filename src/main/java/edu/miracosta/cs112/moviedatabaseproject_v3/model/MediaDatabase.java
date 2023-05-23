package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidTitleException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.MediaDatabaseException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidRatingException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidYearException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The MediaDatabase class represents a database of Media objects.
 * It contains a list of Media objects and methods to add and remove Media objects from the list.
 * It also contains methods to sort the list of Media objects by title, release year, and rating.
 */

public class MediaDatabase {
    private final List<Media> media;

    /**
     * Default constructor which initializes the media list.
     */
    public MediaDatabase() {
        this.media = new ArrayList<>();
    }

    /**
     * Returns the number of Media objects in the database.
     * @return The number of Media objects in the database.
     */

    public void addMedia(Media m) throws MediaDatabaseException, InvalidRatingException, InvalidYearException {
        if (m == null) {
            throw new MediaDatabaseException("Failed to add media. Media object can't be null.");
        }
        if (media.contains(m)) {
            throw new MediaDatabaseException("Media item is already in the database: " + m.getTitle());
        }
        validateMedia(m);
        media.add(m);
    }

    /**
     * Adds a Media object to the database.
     * @throws MediaDatabaseException If the Media object is null or already in the database.
     */

    public void removeMedia(int index) throws MediaDatabaseException {
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Failed to remove media. Invalid index " + index + ". Index must be within the range of the media list.");
        }
        media.remove(index);
    }

    /**
     * Removes a Media object from the database.
     * @param index The index of the Media object to remove.
     * @throws MediaDatabaseException If the index is invalid.
     */

    public void editMedia(int index, Media m) throws MediaDatabaseException, InvalidRatingException, InvalidYearException {
        if (m == null) {
            throw new MediaDatabaseException("Failed to edit media. Media object can't be null.");
        }
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Failed to edit media. Invalid index " + index + ". Index must be within the range of the media list.");
        }
        validateMedia(m);
        media.set(index, m);
    }

    /**
     * Validates the release year and rating of a Media object.
     * @param m The Media object to validate.
     * @throws InvalidYearException If the release year is not between 1888 and the current year.
     * @throws InvalidRatingException If the rating is not between 0.0 and 10.0.
     */

    private void validateMedia(Media m) throws InvalidRatingException, InvalidYearException {
        if (m.getReleaseYear() < 1888 || m.getReleaseYear() > java.time.Year.now().getValue()) {
            throw new InvalidYearException("Invalid release year: " + m.getReleaseYear());
        }
        if (m.getRating() < 0.0 || m.getRating() > 10.0) {
            throw new InvalidRatingException("Invalid rating: " + m.getRating());
        }
    }

    /**
     * Sets the title, release year, and rating of a Media object in the database.
     * @param index The index of the Media object to edit.
     * @param title The new title of the Media object.
     * @param releaseYear The new release year of the Media object.
     * @param rating The new rating of the Media object.
     * @throws MediaDatabaseException If the index is invalid or the parameters are not valid.
     * @throws InvalidTitleException If the title is null or empty.
     * @throws InvalidYearException If the release year is not between 1888 and the current year.
     * @throws InvalidRatingException If the rating is not between 0.0 and 10.0.
     */

    public void setAll(int index, String title, int releaseYear, double rating) throws MediaDatabaseException, InvalidRatingException, InvalidYearException, InvalidTitleException {
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Invalid index " + index + ". Index must be within the range of the media list.");
        }
        Media m = media.get(index);
        m.setTitle(title);
        m.setReleaseYear(releaseYear);
        m.setRating(rating);
        validateMedia(m);
        media.set(index, m);
    }

    /**
     * Returns the Media object at the specified index.
     * @return The Media object at the specified index.
     * @throws MediaDatabaseException If the index is invalid.
     */

    public List<Media> getAllMedia() {
        return new ArrayList<>(media);
    }

    /**
     * Returns a list of all Media objects in the database.
     * @return A list of all Media objects in the database.
     */

    public List<Media> getAllMovies() {
        return media.stream()
                .filter(m -> m instanceof Movie)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all Movie objects in the database.
     * @return A list of all Movie objects in the database.
     */

    public List<Media> getAllTvShows() {
        return media.stream()
                .filter(m -> m instanceof TvShow)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all TvShow objects in the database.
     * @return A list of all TvShow objects in the database.
     */

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

    /**
     * Sorts the Media objects in the database.
     * @throws IllegalArgumentException If the sort option is invalid.
     */

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
