package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import javafx.beans.property.*;

/**
 * The Movie class represents a movie in a media library.
 * It is a subclass of Media and adds a property for duration.
 */

public class Movie extends Media {

    private static final int DEFAULT_DURATION = 90;

    private IntegerProperty duration;

    /**
     * Default constructor. Initializes a movie with default values.
     * @throws InvalidTitleException If the default title is invalid.
     * @throws InvalidYearException If the default year is invalid.
     * @throws InvalidRatingException If the default rating is invalid.
     * @throws InvalidDurationException If the default duration is invalid.
     */

    public Movie() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        super();
        this.duration = new SimpleIntegerProperty(DEFAULT_DURATION);
    }

    /**
     * Full constructor. Initializes a movie with specified values.
     * @param title The title of the movie.
     * @param releaseYear The year the movie was released.
     * @param rating The rating of the movie.
     * @param duration The duration of the movie in minutes.
     * @throws InvalidTitleException If the title is invalid.
     * @throws InvalidYearException If the year is invalid.
     * @throws InvalidRatingException If the rating is invalid.
     * @throws InvalidDurationException If the duration is invalid.
     */

    public Movie(String title, int releaseYear, double rating, int duration) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        super(title, releaseYear, rating);
        this.duration = new SimpleIntegerProperty();
        setDuration(duration);
    }

    /**
     * Copy constructor. Initializes a movie with the values from another movie.
     * @param m The movie to copy values from.
     * @throws InvalidTitleException If the copied title is invalid.
     * @throws InvalidYearException If the copied year is invalid.
     * @throws InvalidRatingException If the copied rating is invalid.
     * @throws InvalidDurationException If the copied duration is invalid.
     */

    public Movie(Movie m) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        super(m);
        this.duration = new SimpleIntegerProperty(m.getDuration());
    }

    /**
     * Short constructor. Initializes a movie with specified values and a default duration.
     * @param title The title of the movie.
     * @param releaseYear The year the movie was released.
     * @param rating The rating of the movie.
     * @throws InvalidTitleException If the title is invalid.
     * @throws InvalidYearException If the year is invalid.
     * @throws InvalidRatingException If the rating is invalid.
     */

    public Movie(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        super(title, releaseYear, rating);
        this.duration = new SimpleIntegerProperty(DEFAULT_DURATION);
    }

    /**
     * Returns the duration of the movie.
     * @return The duration of the movie.
     */

    public int getDuration() {
        return duration.get();
    }

    /**
     * Sets the duration of the movie.
     * @param duration The new duration of the movie.
     * @throws InvalidDurationException If the duration is less than zero.
     */

    public void setDuration(int duration) throws InvalidDurationException {
        if (duration < 0) {
            throw new InvalidDurationException("Invalid duration " + duration + ". Duration must be a positive number.");
        }
        this.duration.set(duration);
    }

    /**
     * Sets all values of the movie.
     * @param title The new title of the movie.
     * @param releaseYear The new year the movie was released.
     * @param rating The new rating of the movie.
     * @param duration The new duration of the movie.
     * @throws InvalidTitleException If the title is invalid.
     * @throws InvalidYearException If the year is invalid.
     * @throws InvalidRatingException If the rating is invalid.
     * @throws InvalidDurationException If the duration is invalid.
     */

    public void setAll(String title, int releaseYear, double rating, int duration) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        setTitle(title);
        setReleaseYear(releaseYear);
        setRating(rating);
        setDuration(duration);
    }

    /**
     * Returns the duration property of the movie.
     * @return The duration property of the movie.
     */

    public IntegerProperty durationProperty() {
        return duration;
    }

    /**
     * equals method. Compares two movies for equality.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Movie movie)) return false;
        return getReleaseYear() == movie.getReleaseYear() &&
                Double.compare(movie.getRating(), getRating()) == 0 &&
                getDuration() == movie.getDuration() &&
                getTitle().equals(movie.getTitle());
    }


    /**
     * hashCode method. Returns the hash code of the movie.
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getDuration();
        result = prime * result + Double.hashCode(getRating());
        result = prime * result + getReleaseYear();
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        return result;
    }

    /**
     * toString method. Returns a string representation of the movie.
     */

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "title='" + getTitle() + '\'' +
                ", releaseYear=" + getReleaseYear() +
                ", rating=" + getRating() +
                ", duration=" + getDuration() +
                '}';
    }
}
