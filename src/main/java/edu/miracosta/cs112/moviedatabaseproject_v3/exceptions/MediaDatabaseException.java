package edu.miracosta.cs112.moviedatabaseproject_v3.exceptions;

public class MediaDatabaseException extends Exception {

    public MediaDatabaseException(String message) {
        super(message);
    }

    public MediaDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
