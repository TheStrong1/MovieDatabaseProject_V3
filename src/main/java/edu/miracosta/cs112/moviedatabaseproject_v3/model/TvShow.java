package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;

import java.util.Objects;
import java.util.Optional;

public class TvShow extends Media {

    private static final int DEFAULT_EPISODES = 5;
    private static final int DEFAULT_SEASONS = 5;

    private int numberOfEpisodes;
    private int numberOfSeasons;

    public TvShow() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        super();
        this.numberOfEpisodes = DEFAULT_EPISODES;
        this.numberOfSeasons = DEFAULT_SEASONS;
    }

    public TvShow(String title, int releaseYear, double rating, int numberOfEpisodes, int numberOfSeasons) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        super(title, releaseYear, rating);
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
        this.numberOfEpisodes = DEFAULT_EPISODES;
        this.numberOfSeasons = DEFAULT_SEASONS;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) throws InvalidNumberOfEpisodesException {
        if (numberOfEpisodes < 0) {
            throw new InvalidNumberOfEpisodesException("Invalid number of episodes " + numberOfEpisodes + ". Number of episodes must be a positive number.");
        }
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) throws InvalidNumberOfSeasonsException {
        if (numberOfSeasons < 0) {
            throw new InvalidNumberOfSeasonsException("Invalid number of seasons " + numberOfSeasons + ". Number of seasons must be a positive number.");
        }
        this.numberOfSeasons = numberOfSeasons;
    }

    public Optional<Double> getAverageEpisodesPerSeason() {
        return numberOfSeasons == 0 ? Optional.empty() : Optional.of((double) numberOfEpisodes / numberOfSeasons);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TvShow tvShow)) return false;
        return getReleaseYear() == tvShow.getReleaseYear() &&
                Double.compare(tvShow.getRating(), getRating()) == 0 &&
                numberOfEpisodes == tvShow.numberOfEpisodes &&
                numberOfSeasons == tvShow.numberOfSeasons &&
                Objects.equals(getTitle(), tvShow.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getReleaseYear(), getRating(), numberOfEpisodes, numberOfSeasons);
    }

    @Override
    public String toString() {
        return "TvShow{" +
                "title='" + getTitle() + '\'' +
                ", releaseYear=" + getReleaseYear() +
                ", rating=" + getRating() +
                ", numberOfEpisodes=" + numberOfEpisodes +
                ", numberOfSeasons=" + numberOfSeasons +
                ", averageEpisodesPerSeason=" + getAverageEpisodesPerSeason().orElse(Double.NaN) +
                '}';
    }
}
