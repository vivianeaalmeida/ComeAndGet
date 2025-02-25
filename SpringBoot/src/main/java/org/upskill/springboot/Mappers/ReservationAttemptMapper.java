package org.upskill.springboot.Mappers;

import jakarta.validation.constraints.NotNull;
import org.upskill.springboot.DTOs.AdvertisementSummaryDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.ReservationAttempt;

import java.time.LocalDate;

/**
 * Mapper class to convert between {@link ReservationAttempt} entity and {@link ReservationAttemptResponseDTO} data transfer object.
 * This class contains methods for transforming data between the entity and DTO representations.
 */
public class ReservationAttemptMapper {
    /**
     * Maps a ReservationAttempt model to a ReservationAttemptResponseDTO.
     *
     * @param reservationAttempt The ReservationAttempt model to be mapped.
     * @return A ReservationAttemptResponseDTO containing the data from the ReservationAttempt model.
     */
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

        //set of the object
        requestDTO.setAdvertisement(advertisementSummaryDTO);
        return requestDTO;
    }

    /**
     * Maps a ReservationAttemptDTO to a ReservationAttempt entity.
     *
     * @param status The status of the reservation attempt.
     * @param clientId The client ID associated with the reservation attempt.
     * @param advertisement The advertisement related to the reservation attempt.
     * @return A ReservationAttempt entity.
     */
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