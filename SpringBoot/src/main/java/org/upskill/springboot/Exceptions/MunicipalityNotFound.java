package org.upskill.springboot.Exceptions;

public class MunicipalityNotFound extends RuntimeException {
    public MunicipalityNotFound(String message) {
        super(message);
    }
}
