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
    private String id;
    @Nullable
    private String title;
    private String description;
    private String status;
    private LocalDate date;


}
