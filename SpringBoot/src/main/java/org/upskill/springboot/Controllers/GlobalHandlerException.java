package org.upskill.springboot.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.upskill.springboot.DTOs.ErrorResponse;
import org.upskill.springboot.Exceptions.*;

/**
 * Global exception handler for the application.
 *
 * This class handles various exceptions thrown by the application and returns appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalHandlerException {

    /**
     * Handles AdvertisementNotFoundException.
     *
     * @param e the AdvertisementNotFoundException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status NOT_FOUND
     */
    @ExceptionHandler(AdvertisementNotFoundException.class)
    public ResponseEntity<ErrorResponse> AdvertisementNotFoundException(AdvertisementNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    /**
     * Handles AdvertisementValidationException.
     *
     * @param e the AdvertisementValidationException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(AdvertisementValidationException.class)
    public ResponseEntity<ErrorResponse> AdvertisementValidationException(AdvertisementValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AdvertisementInvalidActionException.
     *
     * @param e the AdvertisementInvalidActionException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(AdvertisementInvalidActionException.class)
    public ResponseEntity<ErrorResponse> AdvertisementInvalidActionException(AdvertisementInvalidActionException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AdvertisementInvalidLengthException.
     *
     * @param e the AdvertisementInvalidLengthException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(AdvertisementInvalidLengthException.class)
    public ResponseEntity<ErrorResponse> AdvertisementInvalidLengthException(AdvertisementInvalidLengthException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DuplicateCategoryException.
     *
     * @param e the DuplicateCategoryException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCategoryException(DuplicateCategoryException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles CategoryValidationException.
     *
     * @param e the CategoryValidationException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<ErrorResponse> handleCategoryValidationException(CategoryValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles CategoryDeletionException.
     *
     * @param e the CategoryDeletionException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(CategoryDeletionException.class)
    public ResponseEntity<ErrorResponse> handleCategoryValidationException(CategoryDeletionException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles CategoryNotFoundException.
     *
     * @param e the CategoryNotFoundException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status NOT_FOUND
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    /**
     * Handles RequestNotFoundException.
     *
     * @param e the RequestNotFoundException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ReservationAttemptNotFoundException.class)
    public ResponseEntity<ErrorResponse> RequestNotFoundExceptions(ReservationAttemptNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles NotNullException.
     *
     * @param e the NotNullException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(NotNullException.class)
    public ResponseEntity<ErrorResponse> handleNotNullException(NotNullException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ItemNotFoundException.
     *
     * @param e the ItemNotFoundException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemValidationException(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ItemValidationException.
     *
     * @param e the ItemValidationException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(ItemValidationException.class)
    public ResponseEntity<ErrorResponse> handleItemValidationException(ItemValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ClientNotFoundException.
     *
     * @param e the ClientNotFoundException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param e the IllegalArgumentException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IllegalStateException.
     *
     * @param e the IllegalStateException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic Exception.
     *
     * @param e the Exception
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
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
