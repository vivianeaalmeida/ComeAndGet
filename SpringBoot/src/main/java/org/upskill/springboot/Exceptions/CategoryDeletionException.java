package org.upskill.springboot.Exceptions;

public class CategoryDeletionException extends RuntimeException {
    public CategoryDeletionException(String message) {
        super(message);
    }
}
