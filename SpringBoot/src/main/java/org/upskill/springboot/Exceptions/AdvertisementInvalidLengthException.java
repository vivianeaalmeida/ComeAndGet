package org.upskill.springboot.Exceptions;

public class AdvertisementInvalidLengthException extends RuntimeException {
    public AdvertisementInvalidLengthException(String message) {
        super(message);
    }
}
