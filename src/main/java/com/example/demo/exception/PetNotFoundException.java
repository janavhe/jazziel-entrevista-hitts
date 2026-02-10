package com.example.demo.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String id) {
        super("Pet not found with id: " + id);
    }
}
