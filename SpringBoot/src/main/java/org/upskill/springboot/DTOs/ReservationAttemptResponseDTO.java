package org.upskill.springboot.DTOs;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for reservation attempt responses.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAttemptResponseDTO {

    /** The ID of the reservation attempt. */
    private String id;

    /** The ID of the client making the reservation attempt (nullable). */
    @Nullable
    private String clientId;

    /** The status of the reservation attempt. */
    private String status;

    /** The ID of the advertisement associated with the reservation attempt. */
    private String advertisementId;

    /** The date of the reservation attempt. */
    private LocalDate date;

    /** A summary of the advertisement related to this reservation attempt. */
    private AdvertisementSummaryDTO advertisement;
}
