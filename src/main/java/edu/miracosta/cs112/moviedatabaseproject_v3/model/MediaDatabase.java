package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

public class MediaDatabase {
    private final List<Media> media;

    public MediaDatabase() {
        this.media = new ArrayList<>();
    }

    public void addMedia(Media m) throws MediaDatabaseException {
        if (m == null) {
            throw new MediaDatabaseException("Failed to add media. Media object can't be null.");
        }
        if (media.contains(m)) {
            throw new MediaDatabaseException("Media item is already in the database: " + m.getTitle());
        }
        validateMedia(m);
        media.add(m);
    }

    public void removeMedia(int index) throws MediaDatabaseException {
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Failed to remove media. Invalid index " + index + ". Index must be within the range of the media list.");
        }
        media.remove(index);
    }

    public void editMedia(int index, Media m) throws MediaDatabaseException {
        if (m == null) {
            throw new MediaDatabaseException("Failed to edit media. Media object can't be null.");
        }
        if (index < 0 || index >= media.size()) {
            throw new MediaDatabaseException("Failed to edit media. Invalid index " + index + ". Index must be within the range of the media list.");
        }
        validateMedia(m);
        media.set(index, m);
    }

    private void validateMedia(Media m) throws MediaDatabaseException {
        if (m.getReleaseYear() < 1888 || m.getReleaseYear() > java.time.Year.now().getValue()) {
            throw new MediaDatabaseException("Invalid release year: " + m.getReleaseYear());
        }
        if (m.getRating() < 0.0 || m.getRating() > 10.0) {
            throw new MediaDatabaseException("Invalid rating: " + m.getRating());
        }
    }

    public List<Media> getAllMedia(String searchText) {
        return searchMedia(searchText, media);
    }

    public List<Media> getAllMovies(String searchText) {
        return searchMedia(searchText, media.stream()
                .filter(m -> m instanceof Movie)
                .collect(Collectors.toList()));
    }

    public List<Media> getAllTvShows(String searchText) {
        return searchMedia(searchText, media.stream()
                .filter(m -> m instanceof TvShow)
                .collect(Collectors.toList()));
    }

    private List<Media> searchMedia(String searchText, List<Media> mediaList) {
        String searchLowercase = searchText.toLowerCase();
        return mediaList.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(searchLowercase) ||
                        Integer.toString(m.getReleaseYear()).contains(searchText) ||
                        (m.getRating() + "").contains(searchText) ||
                        (m instanceof Movie && Integer.toString(((Movie) m).getDuration()).contains(searchText)) ||
                        (m instanceof TvShow && (Integer.toString(((TvShow) m).getNumberOfSeasons()).contains(searchText) ||
                                Integer.toString(((TvShow) m).getNumberOfEpisodes()).contains(searchText))))
                .collect(Collectors.toList());
    }


    public void sortMedia(SortOption sortOption) {
        if ((sortOption == SortOption.DURATION_ASCENDING || sortOption == SortOption.DURATION_DESCENDING) &&
                media.stream().anyMatch(m -> !(m instanceof Movie))) {
            throw new IllegalArgumentException("Cannot sort by duration if list contains non-Movie items.");
        }
        if ((sortOption == SortOption.SEASONS_ASCENDING || sortOption == SortOption.SEASONS_DESCENDING ||
                sortOption == SortOption.EPISODES_ASCENDING || sortOption == SortOption.EPISODES_DESCENDING) &&
                media.stream().anyMatch(m -> !(m instanceof TvShow))) {
            throw new IllegalArgumentException("Cannot sort by seasons or episodes if list contains non-TvShow items.");
        }

        Comparator<Media> comparator = switch (sortOption) {
            case TITLE_ASCENDING -> Comparator.comparing(Media::getTitle);
            case TITLE_DESCENDING -> Comparator.comparing(Media::getTitle).reversed();
            case YEAR_ASCENDING -> Comparator.comparingInt(Media::getReleaseYear);
            case YEAR_DESCENDING -> Comparator.comparingInt(Media::getReleaseYear).reversed();
            case RATING_ASCENDING -> Comparator.comparingDouble(Media::getRating);
            case RATING_DESCENDING -> Comparator.comparingDouble(Media::getRating).reversed();
            case DURATION_ASCENDING -> Comparator.comparingInt((Media m) -> ((Movie) m).getDuration());
            case DURATION_DESCENDING -> (m1, m2) -> Integer.compare(((Movie) m2).getDuration(), ((Movie) m1).getDuration());
            case SEASONS_ASCENDING -> Comparator.comparingInt((Media m) -> ((TvShow) m).getNumberOfSeasons());
            case SEASONS_DESCENDING -> (m1, m2) -> Integer.compare(((TvShow) m2).getNumberOfSeasons(), ((TvShow) m1).getNumberOfSeasons());
            case EPISODES_ASCENDING -> Comparator.comparingInt((Media m) -> ((TvShow) m).getNumberOfEpisodes());
            case EPISODES_DESCENDING -> (m1, m2) -> Integer.compare(((TvShow) m2).getNumberOfEpisodes(), ((TvShow) m1).getNumberOfEpisodes());
            default -> throw new IllegalArgumentException("Invalid sort option");
        };

        media.sort(comparator);
    }

    public enum SortOption {
        TITLE_ASCENDING,
        TITLE_DESCENDING,
        YEAR_ASCENDING,
        YEAR_DESCENDING,
        RATING_ASCENDING,
        RATING_DESCENDING,
        DURATION_ASCENDING,
        DURATION_DESCENDING,
        SEASONS_ASCENDING,
        SEASONS_DESCENDING,
        EPISODES_ASCENDING,
        EPISODES_DESCENDING
    }
}

