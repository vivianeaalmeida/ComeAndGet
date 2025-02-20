package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.Models.ReservationAttempt;

public class ReservationAttemptMapper {

    public static ReservationAttemptResponseDTO toDTO(ReservationAttempt reservationAttempt) {
        ReservationAttemptResponseDTO requestDTO = new ReservationAttemptResponseDTO();
        requestDTO.setId(reservationAttempt.getId());
        requestDTO.setStatus(reservationAttempt.getStatus().toString());
        requestDTO.setUserId(reservationAttempt.getClientId());
        requestDTO.setDate(reservationAttempt.getDate());
        requestDTO.setAdvertisementId(reservationAttempt.getAdvertisement().getId());
        return requestDTO;

    }

    public static ReservationAttempt toEntity(ReservationAttemptDTO reservationAttemptDTO) {
        ReservationAttempt reservationAttempt = new ReservationAttempt();
        reservationAttempt.setClientId(reservationAttemptDTO.getUserId());
        //reservationAttempt.setAdvertisement(reservationAttemptDTO.getAdvertisementId());

        if(reservationAttemptDTO.getStatus() != null) {
            reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.valueOf(reservationAttemptDTO.getStatus().toUpperCase()));
        }
        return reservationAttempt;
    }

}

//requestDTO.setStatus(request.getStatus());
//requestDTO.setEmail(request.getEmail());
//requestDTO.setPhone(request.setPhone);