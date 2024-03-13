package com.spring_rest.demo.exceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String id) {
        super("Room not found with id: " + id);
    }
}

