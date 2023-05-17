package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

public class InvalidYearException extends Exception {
    public InvalidYearException() {
        super("Invalid year. Year must be a positive integer.");
    }

    public InvalidYearException(String message) {
        super(message);
    }
}
