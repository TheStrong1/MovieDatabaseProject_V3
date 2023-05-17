package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MediaDatabaseTest {

    @Test
    public void testAddMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        Assertions.assertEquals(1, db.getAllMedia("").size());
    }

    @Test
    public void testRemoveMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        db.removeMedia(0);
        Assertions.assertEquals(0, db.getAllMedia("").size());
    }

    @Test
    public void testEditMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        Movie m2 = new Movie("Movie 2", 2001, 9.0, 130);
        db.editMedia(0, m2);
        Assertions.assertEquals(m2, db.getAllMedia("").get(0));
    }

    @Test
    public void testDuplicateAddMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m1);
        Assertions.assertThrows(MediaDatabaseException.class, () -> {
            db.addMedia(m1);
        });
    }

    @Test
    public void testCaseInsensitiveSearchMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie 1", 2000, 8.0, 120);
        Movie m2 = new Movie("movie 2", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        Assertions.assertEquals(2, db.getAllMedia("MoViE").size());
    }


    @Test
    public void testSearchMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie 1", 2000, 8.0, 120);
        Movie m2 = new Movie("Movie 2", 2000, 8.0, 120);
        Movie m3 = new Movie("Other Movie", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        Assertions.assertEquals(3, db.getAllMedia("Movie").size());

    }

    @Test
    public void testSearchMediaOnPartialTitle() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("The Godfather", 1972, 9.2, 175);
        Movie m2 = new Movie("The Godfather: Part II", 1974, 9.0, 202);
        db.addMedia(m1);
        db.addMedia(m2);
        Assertions.assertEquals(2, db.getAllMedia("Godfather").size());
    }

    @Test
    public void testSortMediaByYearDescending() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie A", 1999, 8.0, 120);
        Movie m2 = new Movie("Movie B", 2001, 8.0, 120);
        Movie m3 = new Movie("Movie C", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        db.sortMedia(MediaDatabase.SortOption.YEAR_DESCENDING);

        List<Media> sortedMedia = db.getAllMedia("");
        Assertions.assertEquals(3, sortedMedia.size());

        Assertions.assertEquals(2001, sortedMedia.get(0).getReleaseYear());
        Assertions.assertEquals("Movie B", sortedMedia.get(0).getTitle());

        Assertions.assertEquals(2000, sortedMedia.get(1).getReleaseYear());
        Assertions.assertEquals("Movie C", sortedMedia.get(1).getTitle());

        Assertions.assertEquals(1999, sortedMedia.get(2).getReleaseYear());
        Assertions.assertEquals("Movie A", sortedMedia.get(2).getTitle());
    }



    @Test
    public void testSortMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie B", 2000, 8.0, 120);
        Movie m2 = new Movie("Movie A", 2000, 8.0, 120);
        Movie m3 = new Movie("Movie C", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        db.sortMedia(MediaDatabase.SortOption.TITLE_ASCENDING);
        Assertions.assertEquals("Movie A", db.getAllMedia("").get(0).getTitle());
    }

    @Test
    public void testInvalidAddMedia() {
        MediaDatabase db = new MediaDatabase();
        Assertions.assertThrows(InvalidTitleException.class, () -> {
            Movie m = new Movie("", 2000, 8.0, 120);
            db.addMedia(m);
        });
    }

    @Test
    public void testInvalidEditMedia() {
        MediaDatabase db = new MediaDatabase();
        Assertions.assertThrows(InvalidTitleException.class, () -> {
            Movie m = new Movie("Movie 1", 2000, 8.0, 120);
            db.addMedia(m);
            Movie m2 = new Movie("", 2001, 9.0, 130);
            db.editMedia(0, m2);
        });
    }

    @Test
    public void testInvalidRemoveMedia() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        Assertions.assertThrows(MediaDatabaseException.class, () -> {
            db.removeMedia(1);
        });
    }

    @Test
    public void testAddMultipleMedia() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie 1", 2000, 8.0, 120);
        Movie m2 = new Movie("Movie 2", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        Assertions.assertEquals(2, db.getAllMedia("").size());
    }

    @Test
    public void testRemoveNonexistentMedia() {
        MediaDatabase db = new MediaDatabase();
        Assertions.assertThrows(MediaDatabaseException.class, () -> {
            db.removeMedia(0);
        });
    }

    @Test
    public void testSortMediaByRating() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie A", 2000, 7.0, 120);
        Movie m2 = new Movie("Movie B", 2000, 8.0, 120);
        Movie m3 = new Movie("Movie C", 2000, 9.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        db.sortMedia(MediaDatabase.SortOption.RATING_ASCENDING);

        List<Media> sortedMedia = db.getAllMedia("");
        Assertions.assertEquals(3, sortedMedia.size());

        Assertions.assertEquals(7.0, sortedMedia.get(0).getRating());
        Assertions.assertEquals(8.0, sortedMedia.get(1).getRating());
        Assertions.assertEquals(9.0, sortedMedia.get(2).getRating());
    }



    @Test
    public void testSortMediaByYear() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, MediaDatabaseException {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie B", 2002, 8.0, 120);
        Movie m2 = new Movie("Movie A", 2000, 8.0, 120);
        Movie m3 = new Movie("Movie C", 2001, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        db.sortMedia(MediaDatabase.SortOption.YEAR_ASCENDING);

        List<Media> sortedMedia = db.getAllMedia("");
        Assertions.assertEquals(3, sortedMedia.size());

        Assertions.assertEquals(2000, sortedMedia.get(0).getReleaseYear());
        Assertions.assertEquals("Movie A", sortedMedia.get(0).getTitle());

        Assertions.assertEquals(2001, sortedMedia.get(1).getReleaseYear());
        Assertions.assertEquals("Movie C", sortedMedia.get(1).getTitle());

        Assertions.assertEquals(2002, sortedMedia.get(2).getReleaseYear());
        Assertions.assertEquals("Movie B", sortedMedia.get(2).getTitle());
    }
}