package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;
import edu.miracosta.cs112.moviedatabaseproject_v3.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceTest {
    private SearchService searchService;
    private List<Media> mediaList;

    @BeforeEach
    public void setUp() throws InvalidYearException, InvalidDurationException, InvalidTitleException, InvalidRatingException, InvalidNumberOfSeasonsException, InvalidNumberOfEpisodesException {
        searchService = new SearchService();
        mediaList = new ArrayList<>();
        mediaList.add(new Movie("Matrix", 1999, 8.7, 136));
        mediaList.add(new TvShow("Friends", 1994, 8.9, 10, 236));
    }

    @Test
    public void testSearchMediaWithTitle() {
        List<Media> results = searchService.searchMedia("Matrix", mediaList);
        assertEquals(1, results.size());
        assertEquals("Matrix", results.get(0).getTitle());
    }

    @Test
    public void testSearchMediaWithYear() {
        List<Media> results = searchService.searchMedia("1994", mediaList);
        assertEquals(1, results.size());
        assertEquals("Friends", results.get(0).getTitle());
    }

    @Test
    public void testSearchMediaWithRating() {
        List<Media> results = searchService.searchMedia("8.7", mediaList);
        assertEquals(1, results.size());
        assertEquals("Matrix", results.get(0).getTitle());
    }

    @Test
    public void testSearchMediaWithEmptySearchText() {
        List<Media> results = searchService.searchMedia("", mediaList);
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchMediaWithNullSearchText() {
        List<Media> results = searchService.searchMedia(null, mediaList);
        assertEquals(2, results.size());
    }
}
