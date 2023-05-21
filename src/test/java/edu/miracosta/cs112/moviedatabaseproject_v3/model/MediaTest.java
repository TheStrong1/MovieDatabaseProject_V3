package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaTest {

    private MediaTestStub media;

    @BeforeEach
    void setUp() {
        try {
            media = new MediaTestStub("Test Title", 2020, 8.5);
        } catch (InvalidTitleException | InvalidYearException | InvalidRatingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSetTitle() {
        try {
            media.setTitle("New Title");
            assertEquals("New Title", media.getTitle());
        } catch (InvalidTitleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSetInvalidTitle() {
        assertThrows(InvalidTitleException.class, () -> media.setTitle(""));
        assertThrows(InvalidTitleException.class, () -> media.setTitle(null));
        assertThrows(InvalidTitleException.class, () -> media.setTitle("Invalid Title %%%"));
    }

    @Test
    void testSetReleaseYear() {
        try {
            media.setReleaseYear(2022);
            assertEquals(2022, media.getReleaseYear());
        } catch (InvalidYearException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSetInvalidReleaseYear() {
        assertThrows(InvalidYearException.class, () -> media.setReleaseYear(-1));
        assertThrows(InvalidYearException.class, () -> media.setReleaseYear(java.time.Year.now().getValue() + 1));
    }

    @Test
    void testSetRating() {
        try {
            media.setRating(7.0);
            assertEquals(7.0, media.getRating());
        } catch (InvalidRatingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSetInvalidRating() {
        assertThrows(InvalidRatingException.class, () -> media.setRating(-1.0));
        assertThrows(InvalidRatingException.class, () -> media.setRating(10.1));
    }

    @Test
    void testEquals() {
        try {
            MediaTestStub sameMedia = new MediaTestStub("Test Title", 2020, 8.5);
            assertEquals(media, sameMedia);

            MediaTestStub differentTitle = new MediaTestStub("Different Title", 2020, 8.5);
            assertNotEquals(media, differentTitle);

            MediaTestStub differentYear = new MediaTestStub("Test Title", 2019, 8.5);
            assertNotEquals(media, differentYear);

            MediaTestStub differentRating = new MediaTestStub("Test Title", 2020, 7.5);
            assertNotEquals(media, differentRating);

        } catch (InvalidTitleException | InvalidYearException | InvalidRatingException e) {
            e.printStackTrace();
        }
    }
}
