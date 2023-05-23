package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import javafx.beans.property.*;

import java.util.Objects;


/**
 * Abstract class representing a piece of media, such as a Movie or TV Show.
 * It implements several key properties and methods that are common to all types of media,
 * including title, release year, and rating.
 */


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


    /**
     * Default constructor which sets default values for the media's title, release year, and rating.
     */

    public Media(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        this();
        setTitle(title);
        setReleaseYear(releaseYear);
        setRating(rating);
    }

    /**
     * Copy constructor which copies the title, release year, and rating from another Media object.
     * @param m The Media object to copy.
     */

    public Media(Media m) {
        this.title = new SimpleStringProperty(m.getTitle());
        this.releaseYear = new SimpleIntegerProperty(m.getReleaseYear());
        this.rating = new SimpleDoubleProperty(m.getRating());
    }

    /**
     * Getter and setter methods for title, release year, and rating, including checks for valid inputs.
     * Any setter method may throw an exception if the provided value is not valid
     * Returns the title of the media.
     * @return The title of the media.
     */

    public String getTitle() {
        return title.get();
    }

    /**
     * Sets the title of the media.
     * @param title The title of the media.
     * @throws InvalidTitleException if the title is null, empty, or contains invalid characters.
     */

    public void setTitle(String title) throws InvalidTitleException {
        if (title == null || title.trim().equals("")) {
            throw new InvalidTitleException("Title can't be null or empty.");
        } else if (!title.matches("[a-zA-Z0-9 .,!?\\-_:;()\\[\\]+=']+")) {
            throw new InvalidTitleException("Title contains invalid characters.");
        }
        this.title.set(title);
    }

    /**
     * Returns the title property of the media.
     * @return The title property of the media.
     */

    public StringProperty titleProperty() {
        return title;
    }


    /**
     * Returns the release year of the media.
     * @return The release year of the media.
     */

    public int getReleaseYear() {
        return releaseYear.get();
    }

    /**
     * Sets the release year of the media.
     * @param releaseYear The release year of the media.
     * @throws InvalidYearException if the release year is not a positive number or is in the future.
     */

    public void setReleaseYear(int releaseYear) throws InvalidYearException {
        if (releaseYear <= 0 || releaseYear > java.time.Year.now().getValue()) {
            throw new InvalidYearException("Invalid year " + releaseYear + ". Year must be a positive number and not in the future.");
        }
        this.releaseYear.set(releaseYear);
    }

    /**
     * Sets all properties of the media.
     * @param title
     * @param releaseYear
     * @param rating
     * @throws InvalidTitleException
     * @throws InvalidYearException
     * @throws InvalidRatingException
     */

    public void setAll(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        setTitle(title);
        setReleaseYear(releaseYear);
        setRating(rating);
    }


    /**
     * Returns the release year property of the media.
     * @return The release year property of the media.
     */

    public IntegerProperty releaseYearProperty() {
        return releaseYear;
    }

    /**
     * Returns the rating of the media.
     * @return The rating of the media.
     */

    public double getRating() {
        return rating.get();
    }

    /**
     * Sets the rating of the media.
     * @param rating The rating of the media.
     * @throws InvalidRatingException if the rating is not between 0.0 and 10.0.
     */

    public void setRating(double rating) throws InvalidRatingException {
        if (rating < 0.0 || rating > 10.0) {
            throw new InvalidRatingException("Invalid rating " + rating + ". Rating must be between 0.0 and 10.0.");
        }
        this.rating.set(rating);
    }

    /**
     * Returns the rating property of the media.
     * @return The rating property of the media.
     */

    public DoubleProperty ratingProperty() {
        return rating;
    }

    /**
     * Returns a hash code for the media.
     * @return A hash code for the media.
     */

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getReleaseYear(), getRating());
    }

    /**
     * Abstract equals method to be overridden in subclasses.
     * It checks if the provided object is equal to this media.
     */

    @Override
    public abstract boolean equals(Object obj);

    /**
     * Provides a String representation of this media object, including its class name, title, release year, and rating.
     */

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "title=" + getTitle() +
                ", releaseYear=" + getReleaseYear() +
                ", rating=" + getRating() +
                '}';
    }

}
