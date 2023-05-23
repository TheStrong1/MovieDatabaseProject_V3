package edu.miracosta.cs112.moviedatabaseproject_v3.service;

import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The SearchService class provides functionality to search through media items.
 * This class was implemented to encapsulate the logic for searching media items
 * based on a search text input.
 * It allows for a more organized and efficient way to manage the searching functionality
 * of the media items within the application, keeping the searching logic separate
 * from the main application code.
 * This separation of concerns aids in code readability, maintainability, and re-usability.
 */

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
                        searchInt.map(val -> val.equals(m.getReleaseYear())).orElse(false) ||
                        searchInt.map(val -> (m instanceof Movie && ((Movie) m).getDuration() == val) ||
                                (m instanceof TvShow && (((TvShow) m).getNumberOfSeasons() == val ||
                                        ((TvShow) m).getNumberOfEpisodes() == val))).orElse(false) ||
                        searchDouble.map(val -> val.equals(m.getRating())).orElse(false))
                .collect(Collectors.toList());
    }

    /**
     * Converts a String to an Integer if possible.
     *
     * @param value The String to convert.
     * @return An Optional containing the Integer if the String could be converted, or an empty Optional otherwise.
     */
    private Optional<Integer> toInteger(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Converts a String to a Double if possible.
     *
     * @param value The String to convert.
     * @return An Optional containing the Double if the String could be converted, or an empty Optional otherwise.
     */
    private Optional<Double> toDouble(String value) {
        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
