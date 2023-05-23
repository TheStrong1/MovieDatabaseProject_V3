package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

/**
 * InvalidRatingException represents the exception that is thrown when a rating is invalid.
 */

public class InvalidRatingException extends Exception {
    public InvalidRatingException() {
        super("Invalid rating. Rating must be between 0.0 and 10.0.");
    }

    public InvalidRatingException(String message) {
        super(message);
    }
}
