package edu.miracosta.cs112.moviedatabaseproject_v3.service;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest {
    SearchService searchService;
    List<Media> mediaList;

    @BeforeEach
    void setUp() throws InvalidYearException, InvalidTitleException, InvalidNumberOfSeasonsException, InvalidRatingException, InvalidNumberOfEpisodesException, InvalidDurationException {
        searchService = new SearchService();
        mediaList = new ArrayList<>();

        // Add some Movie and TvShow objects to mediaList
        mediaList.add(new Movie("Title1", 2001, 8.0, 120));
        mediaList.add(new Movie("Title2", 2005, 7.5, 100));
        mediaList.add(new TvShow("Title3", 2010, 9.0, 10, 100));
    }

    @Test
    void searchMedia_emptyOrNullSearchText() {
        // Test with null search text
        List<Media> results = searchService.searchMedia(null, mediaList);
        // Expecting the original list back since the search text is null
        assertEquals(mediaList, results);

        // Test with empty search text
        results = searchService.searchMedia("", mediaList);
        // Expecting the original list back since the search text is empty
        assertEquals(mediaList, results);
    }

    @Test
    void searchMedia_nonEmptySearchText() {
        // Test with non-empty search text
        List<Media> results = searchService.searchMedia("Title1", mediaList);
        // Expecting only the first Media item since its title matches the search text
        assertEquals(1, results.size());
        assertEquals("Title1", results.get(0).getTitle());
    }

    @Test
    void searchMedia_searchYear() {
        // Test search by year
        List<Media> results = searchService.searchMedia("2005", mediaList);
        // Expecting only the Movie item since its release year matches the search text
        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof Movie);
        assertEquals(2005, results.get(0).getReleaseYear());
    }

    /*@Test
    void searchMedia_searchRating() {
        // Test search by rating
        List<Media> results = searchService.searchMedia("9.0", mediaList);
        // Expecting the Media and TvShow items since their ratings match the search text
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(m -> m.getTitle().equals("Title1")));
        assertTrue(results.stream().anyMatch(m -> m.getTitle().equals("Title3")));
    }*/

    @Test
    void searchMedia_searchDuration() {
        // Test search by duration
        List<Media> results = searchService.searchMedia("120", mediaList);
        // Expecting only the Movie item since its duration matches the search text
        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof Movie);
        assertEquals(120, ((Movie) results.get(0)).getDuration());
    }

    /*
    @Test
    void searchMedia_searchNumberOfSeasons() {
        // Test search by number of seasons
        List<Media> results = searchService.searchMedia("10", mediaList);
        // Expecting only the TvShow item since its number of seasons matches the search text
        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof TvShow);
        assertEquals(10, ((TvShow) results.get(0)).getNumberOfSeasons());
    }
     */

    /*
    @Test
    void searchMedia_searchNumberOfEpisodes() {
        // Test search by number of episodes
        List<Media> results = searchService.searchMedia("100", mediaList);
        // Expecting only the TvShow item since its number of episodes matches the search text
        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof TvShow);
        assertEquals(100, ((TvShow) results.get(0)).getNumberOfEpisodes());
    }
    */


    @Test
    void searchMedia_noMatch() {
        // Test search with no matches
        List<Media> results = searchService.searchMedia("Nonexistent", mediaList);
        // Expecting an empty list since the search text does not match anything
        assertEquals(0, results.size());
    }
}
