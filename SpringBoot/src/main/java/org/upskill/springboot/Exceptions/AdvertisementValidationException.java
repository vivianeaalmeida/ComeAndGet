package org.upskill.springboot.Exceptions;

public class AdvertisementValidationException extends RuntimeException {
    public AdvertisementValidationException(String message) {
        super(message);
    }
}
