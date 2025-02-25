package org.upskill.springboot.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.upskill.springboot.DTOs.ErrorResponse;
import org.upskill.springboot.Exceptions.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * Global exception handler for the application.
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
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
     * @return a ResponseEntity WITH HTTP status NOT_FOUND
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Handles RequestNotFoundException.
     *
     * @param e the RequestNotFoundException
     * @return a ResponseEntity with HTTP status NOT_FOUND
     */
    @ExceptionHandler(ReservationAttemptNotFoundException.class)
    public ResponseEntity<ErrorResponse> RequestNotFoundExceptions(ReservationAttemptNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
     * Handles InvalidFileExtensionException
     *
     * @param e the InvalidFileExtensionException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(InvalidFileExtensionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileExtensionException(InvalidFileExtensionException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserUnauthorizedException
     *
     * @param e the UserUnauthorizedException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status FORBIDDEN
     */
    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUserUnauthorizedException(UserUnauthorizedException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
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
     * Handles IOException
     *
     * @param e the IOException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Access Denied exception
     *
     * @param e the Access Denied Exception
     * @return a ResponseEntity containing an ErrorResponse and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles axUploadSizeExceededException
     *
     * @param e the axUploadSizeExceededException
     * @return a ResponseEntity containing an ErrorResponse and HTTP status BAD REQUEST
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse>  handleMaxSizeException(MaxUploadSizeExceededException e) {
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
