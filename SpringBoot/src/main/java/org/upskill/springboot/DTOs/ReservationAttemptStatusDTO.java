package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for reservation attempt status.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAttemptStatusDTO {

    /** The status of the reservation attempt. */
    private String status;
}
