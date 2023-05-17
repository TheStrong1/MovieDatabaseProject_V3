package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        movie1 = new Movie("The Dark Knight", 2008, 9.0, 152);
        movie2 = new Movie("The Dark Knight", 2008, 9.0, 152);
    }

    @Test
    void testEquals() {
        assertEquals(movie1, movie2);
        assertEquals(movie2, movie1);
    }

    @Test
    void testHashCode() {
        assertEquals(movie1.hashCode(), movie2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Movie{title='The Dark Knight', releaseYear=2008, rating=9.0, duration=152}", movie1.toString());
    }

    @Test
    void testInvalidDuration() {
        Exception exception = assertThrows(InvalidDurationException.class, () -> movie1.setDuration(-1));
        assertEquals("Invalid duration -1. Duration must be a positive number.", exception.getMessage());
    }

    @Test
    void testCopyConstructor() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie movie3 = new Movie(movie1);
        assertEquals(movie1, movie3);
    }

    @Test
    void testDefaultConstructor() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidDurationException {
        Movie defaultMovie = new Movie();
        assertAll("Movie",
                () -> assertEquals("Default Title", defaultMovie.getTitle()),
                () -> assertEquals(2012, defaultMovie.getReleaseYear()),
                () -> assertEquals(9.0, defaultMovie.getRating()),
                () -> assertEquals(90, defaultMovie.getDuration())
        );
    }

    @Test
    void testEqualsWithNullAndDifferentType() {
        assertNotEquals(null, movie1);
        assertNotEquals("Some String", movie1);
    }

    @Test
    void testInvalidTitle() {
        Exception exception = assertThrows(InvalidTitleException.class, () -> movie1.setTitle("InvalidTitle!@#"));
        assertEquals("Title contains invalid characters.", exception.getMessage());
    }

    @Test
    void testInvalidYear() {
        Exception exception = assertThrows(InvalidYearException.class, () -> movie1.setReleaseYear(java.time.Year.now().getValue() + 1));
        assertEquals("Invalid year " + (java.time.Year.now().getValue() + 1) + ". Year must be a positive number and not in the future.", exception.getMessage());
    }

    @Test
    void testInvalidRating() {
        Exception exception = assertThrows(InvalidRatingException.class, () -> movie1.setRating(11.0));
        assertEquals("Invalid rating 11.0. Rating must be between 0.0 and 10.0.", exception.getMessage());
    }
}
