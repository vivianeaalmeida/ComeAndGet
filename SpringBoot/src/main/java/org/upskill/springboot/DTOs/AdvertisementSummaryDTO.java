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
public class AdvertisementSummaryDTO {
    /** The unique identifier of the advertisement. */
    private String id;
    /** The title of the advertisement (nullable). */
    @Nullable
    private String title;
    /** The description of the advertisement. */
    private String description;
    /** The status of the advertisement (e.g., active, expired). */
    private String status;
    /** The date when the advertisement was created or last updated. */
    private LocalDate date;
}
