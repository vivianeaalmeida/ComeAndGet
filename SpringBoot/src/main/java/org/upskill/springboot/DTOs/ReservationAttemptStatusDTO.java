package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Data Transfer Object (DTO) for reservation attempt status.
 */
public class ReservationAttemptStatusDTO {
    /** The status of the reservation attempt. */
    private String status;
}
