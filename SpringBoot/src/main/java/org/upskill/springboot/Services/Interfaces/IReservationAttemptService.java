package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;

import java.util.List;

/**
 * Service interface for managing reservation attempts
 */
public interface IReservationAttemptService {

    /**
     * Retrieves all reservation attempts
     *
     * @return a list of all reservation attempts DTOs
     */
    List<ReservationAttemptResponseDTO> getReservationAttempts(
            String reservationAttemptClientId,
       String advertisementClientId,
       String advertisementId);

    /**
     * Retrieves a specific reservation by its ID.
     *
     * @param id the ID of the reservation attempt
     * @return the reservation attempts DTOs corresponding to the given ID
     */
    ReservationAttemptResponseDTO getReservationAttemptById(String id);

    /**
     * Retrieves all reservation attempts associated with a specific advertisement.
     *
     * @param advertisementId The ID of the advertisement.
     * @return A list of reservation attempt DTOs related to the advertisement.
     */
    List<ReservationAttemptResponseDTO> getReservationAttemptsByAdvertisement(String advertisementId);

    /**
     * Creates a new reservation attempt
     *
     * @param reservationAttemptDTO the reservation DTO containing the details of the new request
     * @return the created request response DTOs
     */
    ReservationAttemptResponseDTO createReservationAttempt(ReservationAttemptDTO reservationAttemptDTO, String authorization);


    /**
     * Updates the status of an existing reservation attempt.
     *
     * @param id The ID of the reservation attempt to be updated.
     * @param authorization The authorization of the requesting user.
     * @param reservationAttemptStatusDTO The DTO containing the new status of the reservation attempt.
     * @return The updated reservation attempt DTOs
     */
    ReservationAttemptResponseDTO updateReservationAttemptStatus
    (String id,String authorization, ReservationAttemptStatusDTO reservationAttemptStatusDTO);
}
