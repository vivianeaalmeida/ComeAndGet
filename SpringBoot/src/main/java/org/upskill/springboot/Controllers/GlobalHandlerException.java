package org.upskill.springboot.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.upskill.springboot.DTOs.ErrorResponse;
import org.upskill.springboot.Exceptions.*;

@ControllerAdvice
public class GlobalHandlerException {

    // Advertisements Exceptions
    @ExceptionHandler(AdvertisementNotFoundException.class)
    public ResponseEntity<ErrorResponse> AdvertisementNotFoundException(AdvertisementNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdvertisementValidationException.class)
    public ResponseEntity<ErrorResponse> AdvertisementValidationException(AdvertisementValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdvertisementInvalidActionException.class)
    public ResponseEntity<ErrorResponse> AdvertisementInvalidActionException(AdvertisementInvalidActionException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdvertisementInvalidLengthException.class)
    public ResponseEntity<ErrorResponse> AdvertisementInvalidLengthException(AdvertisementInvalidLengthException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Category Exceptions
    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCategoryException(DuplicateCategoryException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<ErrorResponse> handleCategoryValidationException(CategoryValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryDeletionException.class)
    public ResponseEntity<ErrorResponse> handleCategoryValidationException(CategoryDeletionException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    // Request Exceptions
    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> RequestNotFoundExceptions(RequestNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotNullException.class)
    public ResponseEntity<ErrorResponse> handleNotNullException(NotNullException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Item Exceptions
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemValidationException(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemValidationException.class)
    public ResponseEntity<ErrorResponse> handleItemValidationException(ItemValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Client Exceptions
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Global Exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
