package com.hasan.movieproject.log.director;

public class DirectorDuplicateException extends RuntimeException {
    public DirectorDuplicateException() {
    }

    public DirectorDuplicateException(String message) {
        super(message);
        printStackTrace();
    }
}
