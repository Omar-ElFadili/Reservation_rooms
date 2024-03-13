package com.spring_rest.demo.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String id) {
        super("Writer not found with id: " + id);
    }
}

