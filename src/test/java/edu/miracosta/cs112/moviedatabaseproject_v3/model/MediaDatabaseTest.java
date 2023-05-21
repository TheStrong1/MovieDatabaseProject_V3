package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MediaDatabaseTest {

    @Test
    void addMedia_success() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);
        List<Media> allMedia = db.getAllMedia();

        assertEquals(1, allMedia.size());
        assertEquals(m1, allMedia.get(0));
    }

    @Test
    void addMedia_fail_null() {
        MediaDatabase db = new MediaDatabase();
        assertThrows(MediaDatabaseException.class, () -> db.addMedia(null));
    }

    @Test
    void addMedia_fail_duplicate() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);
        assertThrows(MediaDatabaseException.class, () -> db.addMedia(m1));
    }

    @Test
    void addMedia_fail_invalidYear() {
        MediaDatabase db = new MediaDatabase();
        assertThrows(InvalidYearException.class, () -> db.addMedia(new Movie("Title1", 1887, 9.0, 120)));
        assertThrows(InvalidYearException.class, () -> db.addMedia(new Movie("Title2", java.time.Year.now().getValue() + 1, 9.0, 120)));
    }

    @Test
    void addMedia_fail_invalidRating() {
        MediaDatabase db = new MediaDatabase();
        assertThrows(InvalidRatingException.class, () -> db.addMedia(new Movie("Title1", 2000, -1.0, 120)));
        assertThrows(InvalidRatingException.class, () -> db.addMedia(new Movie("Title2", 2000, 11.0, 120)));
    }

    @Test
    void removeMedia_success() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);

        db.removeMedia(0);
        List<Media> allMedia = db.getAllMedia();

        assertEquals(0, allMedia.size());
    }

    @Test
    void removeMedia_fail_invalidIndex() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);

        assertThrows(MediaDatabaseException.class, () -> db.removeMedia(-1));
        assertThrows(MediaDatabaseException.class, () -> db.removeMedia(1));
    }

    @Test
    void editMedia_success() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);

        Movie m2 = new Movie("Title2", 2001, 8.0, 120);
        db.editMedia(0, m2);
        List<Media> allMedia = db.getAllMedia();

        assertEquals(1, allMedia.size());
        assertEquals(m2, allMedia.get(0));
    }

    @Test
    void editMedia_fail_null() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);

        assertThrows(MediaDatabaseException.class, () -> db.editMedia(0, null));
    }

    @Test
    void editMedia_fail_invalidIndex() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        db.addMedia(m1);

        Movie m2 = new Movie("Title2", 2001, 8.0, 120);
        assertThrows(MediaDatabaseException.class, () -> db.editMedia(-1, m2));
        assertThrows(MediaDatabaseException.class, () -> db.editMedia(1, m2));
    }

    @Test
    void getAllMovies_success() throws MediaDatabaseException, InvalidYearException, InvalidRatingException, InvalidDurationException, InvalidTitleException, InvalidNumberOfSeasonsException, InvalidNumberOfEpisodesException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        TvShow tv1 = new TvShow("Title2", 2001, 8.0, 5, 50);
        Movie m2 = new Movie("Title3", 2002, 7.0, 120);
        db.addMedia(m1);
        db.addMedia(tv1);
        db.addMedia(m2);

        List<Media> allMovies = db.getAllMovies();

        assertEquals(2, allMovies.size());
        assertTrue(allMovies.contains(m1));
        assertTrue(allMovies.contains(m2));
    }

    @Test
    void getAllTvShows_success() throws MediaDatabaseException, InvalidYearException, InvalidRatingException, InvalidTitleException, InvalidNumberOfSeasonsException, InvalidNumberOfEpisodesException, InvalidDurationException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title1", 2000, 9.0, 120);
        TvShow tv1 = new TvShow("Title2", 2001, 8.0, 5, 50);
        Movie m2 = new Movie("Title3", 2002, 7.0, 120);
        db.addMedia(m1);
        db.addMedia(tv1);
        db.addMedia(m2);

        List<Media> allTvShows = db.getAllTvShows();

        assertEquals(1, allTvShows.size());
        assertTrue(allTvShows.contains(tv1));
    }

    @Test
    void sortMedia_titleAscending() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title3", 2000, 9.0, 120);
        Movie m2 = new Movie("Title2", 2001, 9.0, 120);
        Movie m3 = new Movie("Title1", 2002, 9.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);

        db.sortMedia(MediaDatabase.SortOption.TITLE_ASCENDING);
        List<Media> allMedia = db.getAllMedia();

        assertEquals(m3, allMedia.get(0));
        assertEquals(m2, allMedia.get(1));
        assertEquals(m1, allMedia.get(2));
    }

    @Test
    void sortMedia_durationDescending() throws MediaDatabaseException, InvalidYearException, InvalidRatingException, InvalidDurationException, InvalidTitleException {
        MediaDatabase db = new MediaDatabase();

        Movie m1 = new Movie("Title3", 2000, 9.0, 110);
        Movie m2 = new Movie("Title2", 2001, 9.0, 120);
        Movie m3 = new Movie("Title1", 2002, 9.0, 100);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);

        db.sortMedia(MediaDatabase.SortOption.DURATION_DESCENDING);
        List<Media> allMedia = db.getAllMedia();

        assertEquals(m2, allMedia.get(0));
        assertEquals(m1, allMedia.get(1));
        assertEquals(m3, allMedia.get(2));
    }

    // Add more sorting tests here
    // ...

}
