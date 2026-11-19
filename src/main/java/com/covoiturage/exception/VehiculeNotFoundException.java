package com.covoiturage.exception;

public class VehiculeNotFoundException extends RuntimeException {
    public VehiculeNotFoundException(String message) {
        super(message);
    }
}
