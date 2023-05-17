package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

public class InvalidIndexException extends Exception {
    public InvalidIndexException() {
        super("Invalid index. Index must be within the range of the media list.");
    }

    public InvalidIndexException(String message) {
        super(message);
    }
}
