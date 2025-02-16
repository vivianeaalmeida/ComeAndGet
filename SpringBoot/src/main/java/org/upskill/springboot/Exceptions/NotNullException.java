package org.upskill.springboot.Exceptions;

public class NotNullException extends RuntimeException {
    public NotNullException(String message) {
        super(message);
    }
}
