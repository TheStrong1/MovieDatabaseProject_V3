package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TvShowTest {

    private TvShow tvShow;

    @BeforeEach
    public void setUp() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        tvShow = new TvShow("Friends", 1994, 8.9, 236, 10);
    }

    @Test
    void testDefaultConstructor() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        TvShow defaultTvShow = new TvShow();
        assertEquals("Default Title", defaultTvShow.getTitle());
        assertEquals(2012, defaultTvShow.getReleaseYear());
        assertEquals(9.0, defaultTvShow.getRating());
        assertEquals(5, defaultTvShow.getNumberOfEpisodes());
        assertEquals(5, defaultTvShow.getNumberOfSeasons());
    }

    @Test
    public void testConstructor() {
        assertEquals("Friends", tvShow.getTitle());
        assertEquals(1994, tvShow.getReleaseYear());
        assertEquals(8.9, tvShow.getRating());
        assertEquals(236, tvShow.getNumberOfEpisodes());
        assertEquals(10, tvShow.getNumberOfSeasons());
    }

    @Test
    public void testGetAverageEpisodesPerSeason() {
        assertTrue(tvShow.getAverageEpisodesPerSeason().isPresent());
        assertEquals(23.6, tvShow.getAverageEpisodesPerSeason().get(), 0.1);
    }

    @Test
    public void testEqualsAndHashCode() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        TvShow sameTvShow = new TvShow("Friends", 1994, 8.9, 236, 10);
        assertEquals(tvShow, sameTvShow);
        assertEquals(tvShow.hashCode(), sameTvShow.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "TvShow{title='Friends', releaseYear=1994, rating=8.9, numberOfEpisodes=236, numberOfSeasons=10, averageEpisodesPerSeason=23.6}";
        assertEquals(expected, tvShow.toString());
    }

    @Test
    public void testSetAndGet() throws InvalidTitleException, InvalidYearException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidNumberOfSeasonsException {
        tvShow.setTitle("New Title");
        tvShow.setReleaseYear(2000);
        tvShow.setRating(8.0);
        tvShow.setNumberOfEpisodes(100);
        tvShow.setNumberOfSeasons(4);

        assertEquals("New Title", tvShow.getTitle());
        assertEquals(2000, tvShow.getReleaseYear());
        assertEquals(8.0, tvShow.getRating());
        assertEquals(100, tvShow.getNumberOfEpisodes());
        assertEquals(4, tvShow.getNumberOfSeasons());
    }

    @Test
    public void testInvalidTitleException() {
        assertThrows(InvalidTitleException.class, () -> tvShow.setTitle(null));
        assertThrows(InvalidTitleException.class, () -> tvShow.setTitle(""));
    }

    @Test
    public void testInvalidYearException() {
        Exception e1 = assertThrows(InvalidYearException.class, () -> tvShow.setReleaseYear(-100));
        assertEquals("Invalid year -100. Year must be a positive number and not in the future.", e1.getMessage());

        Exception e2 = assertThrows(InvalidYearException.class, () -> tvShow.setReleaseYear(java.time.Year.now().getValue() + 1));
        assertEquals("Invalid year " + (java.time.Year.now().getValue() + 1) + ". Year must be a positive number and not in the future.", e2.getMessage());
    }

    @Test
    public void testInvalidRatingException() {
        assertThrows(InvalidRatingException.class, () -> tvShow.setRating(-1.0));
        assertThrows(InvalidRatingException.class, () -> tvShow.setRating(10.1));
    }

    @Test
    public void testInvalidNumberOfEpisodesException() {
        assertThrows(InvalidNumberOfEpisodesException.class, () -> tvShow.setNumberOfEpisodes(-1));
    }

    @Test
    public void testInvalidNumberOfSeasonsException() {
        assertThrows(InvalidNumberOfSeasonsException.class, () -> tvShow.setNumberOfSeasons(-1));
    }
}

