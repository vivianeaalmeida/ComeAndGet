package org.upskill.springboot.Exceptions;

public class InvalidLengthException extends RuntimeException {
    public InvalidLengthException(String message) {
        super(message);
    }
}
