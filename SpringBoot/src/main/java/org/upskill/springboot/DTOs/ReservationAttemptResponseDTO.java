package org.upskill.springboot.DTOs;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Data Transfer Object (DTO) for reservation attempt responses.
 */
public class ReservationAttemptResponseDTO {
    /** The ID of the reservation attempt. */
    private String id;
    @Nullable
    /** The ID of the client making the reservation attempt (nullable). */
    private String clientId;
    /** The status of the reservation attempt. */
    private String status;
    /** The ID of the advertisement associated with the reservation attempt. */
    private String advertisementId;
    /** The date of the reservation attempt. */
    private LocalDate date;
}
