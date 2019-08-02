package com.hasan.movieproject.log.director;

public class DirectorNotFoundException extends RuntimeException {
    public DirectorNotFoundException() {
    }

    public DirectorNotFoundException(String message) {
        super(message);
    }
}
