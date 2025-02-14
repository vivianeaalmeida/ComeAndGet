package org.upskill.springboot.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.upskill.springboot.DTOs.ErrorResponse;
import org.upskill.springboot.Exceptions.CategoryValidationException;
import org.upskill.springboot.Exceptions.DuplicateCategoryException;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCategoryException(DuplicateCategoryException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCategoryException(CategoryValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCategoryException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
