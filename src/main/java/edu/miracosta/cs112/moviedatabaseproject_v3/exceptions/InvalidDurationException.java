package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

public class InvalidDurationException extends Exception {
    public InvalidDurationException() {
        super("Invalid duration. Duration must be a positive integer.");
    }

    public InvalidDurationException(String message) {
        super(message);
    }
}
