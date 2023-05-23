package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import javafx.beans.property.*;

import java.util.Objects;
import java.util.Optional;

/**
 * The TvShow class represents a TV show in a media library.
 * It is a subclass of Media and adds properties for the number of episodes and seasons.
 */

public class TvShow extends Media {

    private static final int DEFAULT_EPISODES = 5;
    private static final int DEFAULT_SEASONS = 5;

    private IntegerProperty numberOfEpisodes;
    private IntegerProperty numberOfSeasons;

    /**
     * Default constructor. Initializes a TV show with default values.
     * @throws InvalidTitleException If the default title is invalid.
     * @throws InvalidYearException If the default year is invalid.
     * @throws InvalidRatingException If the default rating is invalid.
     * @throws InvalidNumberOfEpisodesException If the default number of episodes is invalid.
     * @throws InvalidNumberOfSeasonsException If the default number of seasons is invalid.
     */

    public TvShow() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        super();
        this.numberOfEpisodes = new SimpleIntegerProperty(DEFAULT_EPISODES);
        this.numberOfSeasons = new SimpleIntegerProperty(DEFAULT_SEASONS);
    }

    /**
     * Full constructor. Initializes a TV show with specified values.
     * @param title The title of the TV show.
     * @param releaseYear The year the TV show was released.
     * @param rating The rating of the TV show.
     * @param numberOfEpisodes The number of episodes in the TV show.
     * @param numberOfSeasons The number of seasons in the TV show.
     * @throws InvalidTitleException If the title is invalid.
     * @throws InvalidYearException If the year is invalid.
     * @throws InvalidRatingException If the rating is invalid.
     * @throws InvalidNumberOfEpisodesException If the number of episodes is invalid.
     * @throws InvalidNumberOfSeasonsException If the number of seasons is invalid.
     */

    public TvShow(String title, int releaseYear, double rating, int numberOfEpisodes, int numberOfSeasons) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        super(title, releaseYear, rating);
        this.numberOfEpisodes = new SimpleIntegerProperty();
        this.numberOfSeasons = new SimpleIntegerProperty();
        setNumberOfEpisodes(numberOfEpisodes);
        setNumberOfSeasons(numberOfSeasons);
    }

    /**
     * Copy constructor. Initializes a TV show with the values from another TV show.
     * @param tvShow The TV show to copy values from.
     */

    public TvShow(TvShow tvShow) {
        super(tvShow);
        this.numberOfEpisodes = new SimpleIntegerProperty(tvShow.getNumberOfEpisodes());
        this.numberOfSeasons = new SimpleIntegerProperty(tvShow.getNumberOfSeasons());
    }


    /**
     * Short constructor. Initializes a TV show with specified values and default numbers of episodes and seasons.
     * @param title The title of the TV show.
     * @param releaseYear The year the TV show was released.
     * @param rating The rating of the TV show.
     * @throws InvalidTitleException If the title is invalid.
     * @throws InvalidYearException If the year is invalid.
     * @throws InvalidRatingException If the rating is invalid.
     */

    public TvShow(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        super(title, releaseYear, rating);
        this.numberOfEpisodes = new SimpleIntegerProperty(DEFAULT_EPISODES);
        this.numberOfSeasons = new SimpleIntegerProperty(DEFAULT_SEASONS);
    }


    /**
     * Gets the number of episodes in the TV show.
     * @return The number of episodes in the TV show.
     */

    public int getNumberOfEpisodes() {
        return numberOfEpisodes.get();
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) throws InvalidNumberOfEpisodesException {
        if (numberOfEpisodes < 0) {
            throw new InvalidNumberOfEpisodesException("Invalid number of episodes " + numberOfEpisodes + ". Number of episodes must be a positive number.");
        }
        this.numberOfEpisodes.set(numberOfEpisodes);
    }

    /**
     * Gets the number of seasons in the TV show.
     * @return The number of seasons in the TV show.
     */
    public int getNumberOfSeasons() {
        return numberOfSeasons.get();
    }

    /**
     * Sets the number of seasons in the TV show.
     * @param numberOfSeasons The number of seasons in the TV show.
     * @throws InvalidNumberOfSeasonsException If the number of seasons is invalid.
     */

    public void setNumberOfSeasons(int numberOfSeasons) throws InvalidNumberOfSeasonsException {
        if (numberOfSeasons < 0) {
            throw new InvalidNumberOfSeasonsException("Invalid number of seasons " + numberOfSeasons + ". Number of seasons must be a positive number.");
        }
        this.numberOfSeasons.set(numberOfSeasons);
    }

    public void setAll(String title, int releaseYear, double rating, int numberOfEpisodes, int numberOfSeasons) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        setTitle(title);
        setReleaseYear(releaseYear);
        setRating(rating);
        setNumberOfEpisodes(numberOfEpisodes);
        setNumberOfSeasons(numberOfSeasons);
    }

    public IntegerProperty numberOfSeasonsProperty() {
        return numberOfSeasons;
    }

    public Optional<Double> getAverageEpisodesPerSeason() {
        return getNumberOfSeasons() == 0 ? Optional.empty() : Optional.of((double) getNumberOfEpisodes() / getNumberOfSeasons());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TvShow tvShow)) return false;
        return getReleaseYear() == tvShow.getReleaseYear() &&
                Double.compare(tvShow.getRating(), getRating()) == 0 &&
                getNumberOfEpisodes() == tvShow.getNumberOfEpisodes() &&
                getNumberOfSeasons() == tvShow.getNumberOfSeasons() &&
                Objects.equals(getTitle(), tvShow.getTitle());
    }

    @Override
    public String toString() {
        return "TvShow{" +
                "title='" + getTitle() + '\'' +
                ", releaseYear=" + getReleaseYear() +
                ", rating=" + getRating() +
                ", numberOfEpisodes=" + getNumberOfEpisodes() +
                ", numberOfSeasons=" + getNumberOfSeasons() +
                ", averageEpisodesPerSeason=" + getAverageEpisodesPerSeason().orElse(Double.NaN) +
                '}';
    }
}
