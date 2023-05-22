package edu.miracosta.cs112.moviedatabaseproject_v3.service;

import edu.miracosta.cs112.moviedatabaseproject_v3.model.Media;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.Movie;
import edu.miracosta.cs112.moviedatabaseproject_v3.model.TvShow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchService {
    private static final double DOUBLE_COMPARISON_THRESHOLD = 1E-6;

    /**
     * Searches through a list of Media items for matches with the given search text.
     *
     * @param searchText The text to search for in the Media items.
     * @param mediaList The list of Media items to search through.
     * @return A list of Media items that match the search text.
     */
    public List<Media> searchMedia(String searchText, List<Media> mediaList) {
        if (searchText == null || searchText.isEmpty() || mediaList == null) {
            return Optional.ofNullable(mediaList).orElse(List.of());
        }

        Optional<Integer> searchInt = toInteger(searchText);
        Optional<Double> searchDouble = toDouble(searchText);

        return mediaList.stream()
                .filter(m -> matchesSearch(m, searchText, searchInt, searchDouble))
                .collect(Collectors.toList());
    }

    private boolean matchesSearch(Media m, String searchText, Optional<Integer> searchInt, Optional<Double> searchDouble) {
        return m.getTitle().equalsIgnoreCase(searchText) ||
                searchInt.map(val -> integerMatchesMedia(m, val)).orElse(false) ||
                searchDouble.map(val -> Math.abs(m.getRating() - val) < DOUBLE_COMPARISON_THRESHOLD).orElse(false);
    }

    private boolean integerMatchesMedia(Media m, int val) {
        return m.getReleaseYear() == val ||
                (m instanceof Movie && ((Movie) m).getDuration() == val) ||
                (m instanceof TvShow && (((TvShow) m).getNumberOfSeasons() == val || ((TvShow) m).getNumberOfEpisodes() == val));
    }

    /**
     * Converts a string to an integer, returning an Optional.
     *
     * @param value The string to convert.
     * @return An Optional containing the integer value, or an empty Optional if the string could not be converted.
     */
    private Optional<Integer> toInteger(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Converts a string to a double, returning an Optional.
     *
     * @param value The string to convert.
     * @return An Optional containing the double value, or an empty Optional if the string could not be converted.
     */
    private Optional<Double> toDouble(String value) {
        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
