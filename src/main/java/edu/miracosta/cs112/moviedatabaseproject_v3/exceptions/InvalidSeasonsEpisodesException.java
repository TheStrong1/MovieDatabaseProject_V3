package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

/**
 * DuplicateMediaException represents the exception that is thrown when a duplicate media is added to the library.
 */

public class InvalidSeasonsEpisodesException extends Exception {
    public InvalidSeasonsEpisodesException() {
        super("Invalid number of seasons/episodes. They must be positive integers.");
    }

    public InvalidSeasonsEpisodesException(String message) {
        super(message);
    }
}
