package org.upskill.springboot.Mappers;

import jakarta.validation.constraints.NotNull;
import org.upskill.springboot.DTOs.AdvertisementSummaryDTO;
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
        //faz o set das informações que a UI necessita para dispor os requests.
        AdvertisementSummaryDTO advertisementSummaryDTO = new AdvertisementSummaryDTO();
        advertisementSummaryDTO.setId(reservationAttempt.getAdvertisement().getId());
        advertisementSummaryDTO.setStatus(reservationAttempt.getAdvertisement().getStatus().toString());
        advertisementSummaryDTO.setDate(reservationAttempt.getAdvertisement().getDate());
        advertisementSummaryDTO.setTitle(reservationAttempt.getAdvertisement().getTitle());
        advertisementSummaryDTO.setDescription(reservationAttempt.getAdvertisement().getDescription());
        //set do objeto
        requestDTO.setAdvertisement(advertisementSummaryDTO);
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