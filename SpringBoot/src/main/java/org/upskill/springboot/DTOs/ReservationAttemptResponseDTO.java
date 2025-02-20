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
public class ReservationAttemptResponseDTO {
    private String id;
    @Nullable
    private String userId;
    private String status;
    private String advertisementId;
    private LocalDate date;
}
