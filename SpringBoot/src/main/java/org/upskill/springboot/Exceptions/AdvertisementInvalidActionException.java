package org.upskill.springboot.Exceptions;

public class AdvertisementInvalidActionException extends RuntimeException {
    public AdvertisementInvalidActionException(String message) {
        super(message);
    }
}
