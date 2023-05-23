package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

/**
 * InvalidDurationException represents the exception that is thrown when the duration is invalid.
 */

public class InvalidDurationException extends Exception {
    public InvalidDurationException() {
        super("Invalid duration. Duration must be a positive integer.");
    }

    public InvalidDurationException(String message) {
        super(message);
    }
}
