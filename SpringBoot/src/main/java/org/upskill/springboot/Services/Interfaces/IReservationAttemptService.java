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
     * @return a list of all reservation response data transfer objects
     */
    List<ReservationAttemptResponseDTO> getReservationAttempts();

    /**
     * Retrieves a specific reservation by its ID.
     *
     * @param id the ID of the reservation attempt
     * @return the reservation attempts response data transfer object corresponding to the given ID
     */
    ReservationAttemptResponseDTO getReservationAttemptById(String id);

    List<ReservationAttemptResponseDTO> getReservationAttemptsByAdvertisement(String advertisementId);

    /**
     * Creates a new reservation attempt
     *
     * @param reservationAttemptDTO the reservation DTO containing the details of the new request
     * @return the created request response data transfer object
     */
    ReservationAttemptResponseDTO createReservationAttempt(ReservationAttemptDTO reservationAttemptDTO, String authorization);


    ReservationAttemptResponseDTO updateReservationAttemptStatus
    (String id,String authorization, ReservationAttemptStatusDTO reservationAttemptStatusDTO);

    List<ReservationAttemptResponseDTO> getAttemptsDoneByClientId(String id);

    List<ReservationAttemptResponseDTO> getAttemptsInMyAdvertisements(String clientId);

}
