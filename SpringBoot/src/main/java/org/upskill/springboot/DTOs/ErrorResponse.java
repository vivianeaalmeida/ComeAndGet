package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Error Response
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    /**
     * The error response message
     */
    private String message;

    /**
     * The error response status
     */
    private int status;

    /**
     * The error response error
     */
    private String error;
}
