package org.upskill.springboot.Exceptions;

public class ItemValidationException extends RuntimeException {
    public ItemValidationException(String message) {
        super(message);
    }
}
