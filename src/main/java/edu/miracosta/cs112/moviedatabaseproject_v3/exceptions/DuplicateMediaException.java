package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

/**
 * DuplicateMediaException represents the exception that is thrown when a duplicate media is added to the library.
 */

public class DuplicateMediaException extends Exception {
    public DuplicateMediaException(String message) {
        super(message);
    }
}
