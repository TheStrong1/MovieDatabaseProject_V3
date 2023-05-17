package edu.miracosta.cs112.moviedatabaseproject_v3;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.*;

public class Main {

    public static void main(String[] args) {
        try {
            testAddMedia();
            testRemoveMedia();
            testEditMedia();
            testSearchMedia();
            testSortMedia();
            testInvalidAddMedia();
            testInvalidEditMedia();
            testInvalidRemoveMedia();
            testAddMultipleMedia();
            testRemoveNonexistentMedia();
            testSortMediaByYear(); // Your new test method
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testAddMedia() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        assert db.getAllMedia("").size() == 1 : "Failed addMedia test";
        System.out.println("Passed addMedia test");
    }

    private static void testRemoveMedia() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        db.removeMedia(0);
        assert db.getAllMedia("").size() == 0 : "Failed removeMedia test";
        System.out.println("Passed removeMedia test");
    }

    private static void testEditMedia() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        Movie m2 = new Movie("Movie 2", 2001, 9.0, 130);
        db.editMedia(0, m2);
        assert db.getAllMedia("").get(0).equals(m2) : "Failed editMedia test";
        System.out.println("Passed editMedia test");
    }

    private static void testSearchMedia() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie 1", 2000, 8.0, 120);
        Movie m2 = new Movie("Movie 2", 2000, 8.0, 120);
        Movie m3 = new Movie("Other Movie", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        assert db.getAllMedia("Movie").size() == 2 : "Failed searchMedia test";
        System.out.println("Passed searchMedia test");
    }

    private static void testSortMedia() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie B", 2000, 8.0, 120);
        Movie m2 = new Movie("Movie A", 2000, 8.0, 120);
        Movie m3 = new Movie("Movie C", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        db.sortMedia(MediaDatabase.SortOption.TITLE_ASCENDING);
        assert db.getAllMedia("").get(0).getTitle().equals("Movie A") : "Failed sortMedia test";
        System.out.println("Passed sortMedia test");
    }

    private static void testInvalidAddMedia() {
        MediaDatabase db = new MediaDatabase();
        try {
            Movie m = new Movie("", 2000, 8.0, 120); // invalid title
            db.addMedia(m);
            System.err.println("Failed invalidAddMedia test");
        } catch (InvalidTitleException | MediaDatabaseException e) {
            System.out.println("Passed invalidAddMedia test");
        } catch (InvalidYearException | InvalidDurationException | InvalidRatingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testInvalidEditMedia() {
        MediaDatabase db = new MediaDatabase();
        try {
            Movie m = new Movie("Movie 1", 2000, 8.0, 120);
            db.addMedia(m);
            Movie m2 = new Movie("", 2001, 9.0, 130); // invalid title
            db.editMedia(0, m2);
            System.err.println("Failed invalidEditMedia test");
        } catch (InvalidTitleException | MediaDatabaseException | InvalidYearException | InvalidRatingException |
                 InvalidDurationException e) {
            System.out.println("Passed invalidEditMedia test");
        }
    }


    private static void testInvalidRemoveMedia() throws MediaDatabaseException, InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException {
        MediaDatabase db = new MediaDatabase();
        Movie m = new Movie("Movie 1", 2000, 8.0, 120);
        db.addMedia(m);
        try {
            db.removeMedia(1); // invalid index
            System.err.println("Failed invalidRemoveMedia test");
        } catch (IndexOutOfBoundsException | MediaDatabaseException e) {
            System.out.println("Passed invalidRemoveMedia test");
        }
    }

    private static void testAddMultipleMedia() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie 1", 2000, 8.0, 120);
        Movie m2 = new Movie("Movie 2", 2000, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        assert db.getAllMedia("").size() == 2 : "Failed addMultipleMedia test";
        System.out.println("Passed addMultipleMedia test");
    }

    private static void testRemoveNonexistentMedia() {
        MediaDatabase db = new MediaDatabase();
        try {
            db.removeMedia(0);
            System.err.println("Failed removeNonexistentMedia test");
        } catch (IndexOutOfBoundsException | MediaDatabaseException e) {
            System.out.println("Passed removeNonexistentMedia test");
        }
    }
    private static void testSortMediaByYear() throws Exception {
        MediaDatabase db = new MediaDatabase();
        Movie m1 = new Movie("Movie B", 2002, 8.0, 120);
        Movie m2 = new Movie("Movie A", 2000, 8.0, 120);
        Movie m3 = new Movie("Movie C", 2001, 8.0, 120);
        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
        db.sortMedia(MediaDatabase.SortOption.YEAR_ASCENDING);

        assert db.getAllMedia("").size() == 3 : "Not all movies are in the list.";

        assert db.getAllMedia("").get(0).getReleaseYear() == 2000 : "First movie is not from the year 2000";
        assert db.getAllMedia("").get(0).getTitle().equals("Movie A") : "First movie is not 'Movie A'";

        assert db.getAllMedia("").get(1).getReleaseYear() == 2001 : "Second movie is not from the year 2001";
        assert db.getAllMedia("").get(1).getTitle().equals("Movie C") : "Second movie is not 'Movie C'";

        assert db.getAllMedia("").get(2).getReleaseYear() == 2002 : "Third movie is not from the year 2002";
        assert db.getAllMedia("").get(2).getTitle().equals("Movie B") : "Third movie is not 'Movie B'";

        System.out.println("Passed sortMediaByYear test");
    }
}


