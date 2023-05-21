package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TvShowTest {
    @Test
    void testDefaultConstructor() {
        try {
            TvShow tvShow = new TvShow();
            assertEquals("Default Title", tvShow.getTitle());
            assertEquals(2012, tvShow.getReleaseYear());
            assertEquals(9.0, tvShow.getRating());
            assertEquals(5, tvShow.getNumberOfEpisodes());
            assertEquals(5, tvShow.getNumberOfSeasons());
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    void testConstructorWithParameters() {
        try {
            TvShow tvShow = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            assertEquals("Breaking Bad", tvShow.getTitle());
            assertEquals(2008, tvShow.getReleaseYear());
            assertEquals(9.5, tvShow.getRating());
            assertEquals(62, tvShow.getNumberOfEpisodes());
            assertEquals(5, tvShow.getNumberOfSeasons());
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    void testConstructorWithNegativeRating() {
        assertThrows(InvalidRatingException.class, () -> new TvShow("Breaking Bad", 2008, -9.5, 62, 5));
    }

    @Test
    void testConstructorWithNegativeEpisodes() {
        assertThrows(InvalidNumberOfEpisodesException.class, () -> new TvShow("Breaking Bad", 2008, 9.5, -62, 5));
    }

    @Test
    void testConstructorWithNegativeSeasons() {
        assertThrows(InvalidNumberOfSeasonsException.class, () -> new TvShow("Breaking Bad", 2008, 9.5, 62, -5));
    }

    @Test
    void testSetNumberOfEpisodes() {
        try {
            TvShow tvShow = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            tvShow.setNumberOfEpisodes(63);
            assertEquals(63, tvShow.getNumberOfEpisodes());
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    void testSetNumberOfSeasons() {
        try {
            TvShow tvShow = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            tvShow.setNumberOfSeasons(6);
            assertEquals(6, tvShow.getNumberOfSeasons());
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    void testAverageEpisodesPerSeason() {
        try {
            TvShow tvShow = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            assertEquals(Optional.of(62.0/5), tvShow.getAverageEpisodesPerSeason());
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    void testEquals() {
        try {
            TvShow tvShow1 = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            TvShow tvShow2 = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            assertEquals(tvShow1, tvShow2);
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    void testNotEquals() {
        try {
            TvShow tvShow1 = new TvShow("Breaking Bad", 2008, 9.5, 62, 5);
            TvShow tvShow2 = new TvShow("Breaking Bad", 2008, 9.5, 63, 5);
            assertNotEquals(tvShow1, tvShow2);
        } catch (Exception e) {
            fail("Should not have thrown any exception.");
        }
    }
}
