package org.upskill.springboot.Exceptions;

public class AdvertisementNotFoundException extends RuntimeException {
    public AdvertisementNotFoundException(String message) {
        super(message);
    }
}
