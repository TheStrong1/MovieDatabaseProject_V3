package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

public class InvalidTitleException extends Exception {
    public InvalidTitleException() {
        super("Invalid title. Title cannot be null or empty.");
    }

    public InvalidTitleException(String message) {
        super(message);
    }
}
