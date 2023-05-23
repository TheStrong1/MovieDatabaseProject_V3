package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

/**
 * InvalidYearException represents the exception that is thrown when a year is invalid.
 */

public class InvalidYearException extends Exception {
    public InvalidYearException() {
        super("Invalid year. Year must be a positive integer.");
    }

    public InvalidYearException(String message) {
        super(message);
    }
}
