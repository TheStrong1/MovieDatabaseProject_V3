package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidDurationException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidRatingException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidTitleException;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.InvalidYearException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    private static final String DEFAULT_TITLE = "Default Title";
    private static final int DEFAULT_RELEASE_YEAR = 2012;
    private static final double DEFAULT_RATING = 9.0;
    private static final int DEFAULT_DURATION = 90;

    @Test
    public void testDefaultConstructor() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie = new Movie();

        assertEquals(DEFAULT_TITLE, movie.getTitle());
        assertEquals(DEFAULT_RELEASE_YEAR, movie.getReleaseYear());
        assertEquals(DEFAULT_RATING, movie.getRating());
        assertEquals(DEFAULT_DURATION, movie.getDuration());
    }

    @Test
    public void testConstructorWithTitleYearRating() throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        Movie movie = new Movie("Test Title", 2022, 8.0);

        assertEquals("Test Title", movie.getTitle());
        assertEquals(2022, movie.getReleaseYear());
        assertEquals(8.0, movie.getRating());
        assertEquals(DEFAULT_DURATION, movie.getDuration());
    }

    @Test
    public void testConstructorWithAllParameters() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie = new Movie("Test Title", 2022, 8.0, 120);

        assertEquals("Test Title", movie.getTitle());
        assertEquals(2022, movie.getReleaseYear());
        assertEquals(8.0, movie.getRating());
        assertEquals(120, movie.getDuration());
    }

    @Test
    public void testCopyConstructor() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie original = new Movie("Test Title", 2022, 8.0, 120);
        Movie copy = new Movie(original);

        assertEquals(original.getTitle(), copy.getTitle());
        assertEquals(original.getReleaseYear(), copy.getReleaseYear());
        assertEquals(original.getRating(), copy.getRating());
        assertEquals(original.getDuration(), copy.getDuration());
    }

    @Test
    public void testSetDurationValid() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie = new Movie();
        movie.setDuration(150);

        assertEquals(150, movie.getDuration());
    }

    @Test
    public void testSetDurationInvalid() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie = new Movie();

        assertThrows(InvalidDurationException.class, () -> movie.setDuration(-10));
    }

    @Test
    public void testEqualsAndHashCode() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie1 = new Movie("Test Title", 2022, 8.0, 120);
        Movie movie2 = new Movie("Test Title", 2022, 8.0, 120);

        assertTrue(movie1.equals(movie2) && movie2.equals(movie1));
        assertEquals(movie1.hashCode(), movie2.hashCode());
    }

    @Test
    public void testToString() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie = new Movie("Test Title", 2022, 8.0, 120);
        String expected = "Movie{title='Test Title', releaseYear=2022, rating=8.0, duration=120}";

        assertEquals(expected, movie.toString());
    }
}
