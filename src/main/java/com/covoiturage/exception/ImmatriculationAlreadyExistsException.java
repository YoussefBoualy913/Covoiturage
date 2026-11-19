package com.covoiturage.exception;

public class ImmatriculationAlreadyExistsException extends RuntimeException {
    public ImmatriculationAlreadyExistsException(String message) {
        super(message);
    }
}
