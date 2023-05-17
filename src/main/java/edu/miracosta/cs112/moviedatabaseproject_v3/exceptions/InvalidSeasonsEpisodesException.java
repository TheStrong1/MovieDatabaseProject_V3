package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

public class InvalidSeasonsEpisodesException extends Exception {
    public InvalidSeasonsEpisodesException() {
        super("Invalid number of seasons/episodes. They must be positive integers.");
    }

    public InvalidSeasonsEpisodesException(String message) {
        super(message);
    }
}
