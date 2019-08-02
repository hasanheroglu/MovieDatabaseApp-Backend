package com.hasan.movieproject.log.movie;

public class MovieDuplicateException extends RuntimeException {
    public MovieDuplicateException() {
    }

    public MovieDuplicateException(String message) {
        super(message);
    }
}
