package org.upskill.springboot.Mappers;

import jakarta.validation.constraints.NotNull;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.ReservationAttempt;

import java.time.LocalDate;

public class ReservationAttemptMapper {

    public static ReservationAttemptResponseDTO toDTO(ReservationAttempt reservationAttempt) {
        ReservationAttemptResponseDTO requestDTO = new ReservationAttemptResponseDTO();
        requestDTO.setId(reservationAttempt.getId());
        requestDTO.setStatus(reservationAttempt.getStatus().toString());
        requestDTO.setClientId(reservationAttempt.getClientId());
        requestDTO.setDate(reservationAttempt.getDate());
        requestDTO.setAdvertisementId(reservationAttempt.getAdvertisement().getId());
        return requestDTO;

    }

    //nenhuma informação, exceto o status, vem do request do usuario (DTO)
    public static ReservationAttempt toEntity(@NotNull String status, @NotNull String clientId, @NotNull Advertisement advertisement ) {
        ReservationAttempt reservationAttempt = new ReservationAttempt();

        if(status != null) {
            reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.valueOf(status.toUpperCase()));
        }
        reservationAttempt.setClientId(clientId);
        reservationAttempt.setAdvertisement(advertisement);
        reservationAttempt.setDate(LocalDate.now());
        return reservationAttempt;
    }

}