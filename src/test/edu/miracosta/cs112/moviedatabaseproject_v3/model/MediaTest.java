package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MediaTest {

    private Media testMedia;

    // Concrete subclass of Media for testing purposes
    private static class ConcreteMedia extends Media {
        public ConcreteMedia() throws InvalidTitleException, InvalidYearException, InvalidRatingException {
            super();
        }

        public ConcreteMedia(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
            super(title, releaseYear, rating);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Media media = (Media) obj;
            return getReleaseYear() == media.getReleaseYear() &&
                    Double.compare(media.getRating(), getRating()) == 0 &&
                    getTitle().equals(media.getTitle());
        }
    }

    @BeforeEach
    void setUp() throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        testMedia = new ConcreteMedia("Test Title", 2000, 8.0);
    }

    @Test
    void testSetTitle() throws InvalidTitleException {
        testMedia.setTitle("New Title");
        assertEquals("New Title", testMedia.getTitle());
    }

    @Test
    void testSetReleaseYear() throws InvalidYearException {
        testMedia.setReleaseYear(2020);
        assertEquals(2020, testMedia.getReleaseYear());
    }

    @Test
    void testSetRating() throws InvalidRatingException {
        testMedia.setRating(9.5);
        assertEquals(9.5, testMedia.getRating());
    }

    @Test
    void testEquals() throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        Media anotherMedia = new ConcreteMedia("Test Title", 2000, 8.0);
        assertEquals(testMedia, anotherMedia);
    }

    @Test
    void testHashCode() {
        int expectedHash = testMedia.hashCode();
        assertEquals(expectedHash, testMedia.hashCode());
    }
}
