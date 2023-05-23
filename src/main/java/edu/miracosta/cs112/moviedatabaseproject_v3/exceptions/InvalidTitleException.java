package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

/**
 * InvalidTitleException represents the exception that is thrown when a title is invalid.
 */
public class InvalidTitleException extends Exception {
    public InvalidTitleException() {
        super("Invalid title. Title cannot be null or empty.");
    }

    public InvalidTitleException(String message) {
        super(message);
    }
}
