package org.upskill.springboot.DTOs;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAttemptDTO {
    @Nullable
    private String userId;
    private String status;
    private String advertisementId;
}
