package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import javafx.beans.property.*;

import java.util.Objects;

public abstract class Media {

    private static final String DEFAULT_TITLE = "Default Title";
    private static final int DEFAULT_YEAR = 2012;
    private static final double DEFAULT_RATING = 9.0;

    protected StringProperty title;
    protected IntegerProperty releaseYear;
    protected DoubleProperty rating;

    public Media() throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        title = new SimpleStringProperty();
        releaseYear = new SimpleIntegerProperty();
        rating = new SimpleDoubleProperty();

        setTitle(DEFAULT_TITLE);
        setReleaseYear(DEFAULT_YEAR);
        setRating(DEFAULT_RATING);
    }

    public Media(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        this();
        setTitle(title);
        setReleaseYear(releaseYear);
        setRating(rating);
    }

    public Media(Media m) {
        this.title = new SimpleStringProperty(m.getTitle());
        this.releaseYear = new SimpleIntegerProperty(m.getReleaseYear());
        this.rating = new SimpleDoubleProperty(m.getRating());
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) throws InvalidTitleException {
        if (title == null || title.trim().equals("")) {
            throw new InvalidTitleException("Title can't be null or empty.");
        } else if (!title.matches("[a-zA-Z0-9 .,!?\\-_:;()\\[\\]+=']+")) {
            throw new InvalidTitleException("Title contains invalid characters.");
        }
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear.get();
    }

    public void setReleaseYear(int releaseYear) throws InvalidYearException {
        if (releaseYear <= 0 || releaseYear > java.time.Year.now().getValue()) {
            throw new InvalidYearException("Invalid year " + releaseYear + ". Year must be a positive number and not in the future.");
        }
        this.releaseYear.set(releaseYear);
    }

    public IntegerProperty releaseYearProperty() {
        return releaseYear;
    }

    public double getRating() {
        return rating.get();
    }

    public void setRating(double rating) throws InvalidRatingException {
        if (rating < 0.0 || rating > 10.0) {
            throw new InvalidRatingException("Invalid rating " + rating + ". Rating must be between 0.0 and 10.0.");
        }
        this.rating.set(rating);
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getReleaseYear(), getRating());
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "title=" + getTitle() +
                ", releaseYear=" + getReleaseYear() +
                ", rating=" + getRating() +
                '}';
    }

}
