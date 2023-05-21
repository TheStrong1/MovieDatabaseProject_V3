package edu.miracosta.cs112.moviedatabaseproject_v3.service;

import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchService {
    /**
     * Searches through a list of Media items for matches with the given search text.
     *
     * @param searchText The text to search for in the Media items.
     * @param mediaList The list of Media items to search through.
     * @return A list of Media items that match the search text.
     */
    public List<Media> searchMedia(String searchText, List<Media> mediaList) {
        // Return all media when searchText is null or empty
        if (searchText == null || searchText.isEmpty()) {
            return new ArrayList<>(mediaList);
        }

        String searchLowercase = searchText.toLowerCase();
        Optional<Integer> searchInt = toInteger(searchText);
        Optional<Double> searchDouble = toDouble(searchText);

        return mediaList.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(searchLowercase) ||
                        searchInt.map(val -> m.getReleaseYear() == val ||
                                (m instanceof Movie && ((Movie) m).getDuration() == val) ||
                                (m instanceof TvShow && (((TvShow) m).getNumberOfSeasons() == val ||
                                        ((TvShow) m).getNumberOfEpisodes() == val))).orElse(false) ||
                        searchDouble.map(val -> m.getRating() == val).orElse(false))
                .collect(Collectors.toList());
    }

    private Optional<Integer> toInteger(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Optional<Double> toDouble(String value) {
        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
