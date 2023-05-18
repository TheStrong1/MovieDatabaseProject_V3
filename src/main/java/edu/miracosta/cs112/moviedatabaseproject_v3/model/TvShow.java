package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import javafx.beans.property.*;

import java.util.Objects;
import java.util.Optional;

public class TvShow extends Media {

    private static final int DEFAULT_EPISODES = 5;
    private static final int DEFAULT_SEASONS = 5;

    private IntegerProperty numberOfEpisodes;
    private IntegerProperty numberOfSeasons;

    public TvShow() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        super();
        this.numberOfEpisodes = new SimpleIntegerProperty(DEFAULT_EPISODES);
        this.numberOfSeasons = new SimpleIntegerProperty(DEFAULT_SEASONS);
    }

    public TvShow(String title, int releaseYear, double rating, int numberOfEpisodes, int numberOfSeasons) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        super(title, releaseYear, rating);
        this.numberOfEpisodes = new SimpleIntegerProperty();
        this.numberOfSeasons = new SimpleIntegerProperty();
        setNumberOfEpisodes(numberOfEpisodes);
        setNumberOfSeasons(numberOfSeasons);
    }

    public TvShow(TvShow tvShow) {
        super(tvShow);
        this.numberOfEpisodes = tvShow.numberOfEpisodes;
        this.numberOfSeasons = tvShow.numberOfSeasons;
    }

    public TvShow(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        super(title, releaseYear, rating);
        this.numberOfEpisodes = new SimpleIntegerProperty(DEFAULT_EPISODES);
        this.numberOfSeasons = new SimpleIntegerProperty(DEFAULT_SEASONS);
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes.get();
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) throws InvalidNumberOfEpisodesException {
        if (numberOfEpisodes < 0) {
            throw new InvalidNumberOfEpisodesException("Invalid number of episodes " + numberOfEpisodes + ". Number of episodes must be a positive number.");
        }
        this.numberOfEpisodes.set(numberOfEpisodes);
    }

    public IntegerProperty numberOfEpisodesProperty() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons.get();
    }

    public void setNumberOfSeasons(int numberOfSeasons) throws InvalidNumberOfSeasonsException {
        if (numberOfSeasons < 0) {
            throw new InvalidNumberOfSeasonsException("Invalid number of seasons " + numberOfSeasons + ". Number of seasons must be a positive number.");
        }
        this.numberOfSeasons.set(numberOfSeasons);
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
