package org.upskill.springboot.Exceptions;

public class ReservationAttemptNotFoundException extends RuntimeException {
    public ReservationAttemptNotFoundException(String message) {
        super(message);
    }
}
