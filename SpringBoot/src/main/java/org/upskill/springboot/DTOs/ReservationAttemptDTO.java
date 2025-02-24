package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for representing a reservation attempt.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAttemptDTO {
    /** The status of the reservation attempt*/
    private String status;
    /** The unique identifier of the advertisement associated with the reservation attempt. */
    private String advertisementId;
}
