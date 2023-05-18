package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import javafx.beans.property.*;

public class Movie extends Media {

    private static final int DEFAULT_DURATION = 90;

    private IntegerProperty duration;

    public Movie() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        super();
        this.duration = new SimpleIntegerProperty(DEFAULT_DURATION);
    }

    public Movie(String title, int releaseYear, double rating, int duration) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        super(title, releaseYear, rating);
        this.duration = new SimpleIntegerProperty();
        setDuration(duration);
    }

    public Movie(Movie m) throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        super(m);
        this.duration = m.duration;
    }

    public Movie(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        super(title, releaseYear, rating);
        this.duration = new SimpleIntegerProperty(DEFAULT_DURATION);
    }


    public int getDuration() {
        return duration.get();
    }

    public void setDuration(int duration) throws InvalidDurationException {
        if (duration < 0) {
            throw new InvalidDurationException("Invalid duration " + duration + ". Duration must be a positive number.");
        }
        this.duration.set(duration);
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Movie)) return false;
        Movie movie = (Movie) obj;
        return getReleaseYear() == movie.getReleaseYear() && Double.compare(movie.getRating(), getRating()) == 0 &&
                getDuration() == movie.getDuration() && getTitle().equals(movie.getTitle());
    }

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
