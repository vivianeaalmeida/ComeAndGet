package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;
import org.upskill.springboot.Models.ReservationAttempt;

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
    Page<ReservationAttemptResponseDTO> getReservationAttempts(int page, int size);

    /**
     * Retrieves a specific reservation by its ID.
     *
     * @param id the ID of the reservation attempt
     * @return the reservation attempts response data transfer object corresponding to the given ID
     */
    ReservationAttemptResponseDTO getReservationAttemptById(String id);

    List<ReservationAttempt> getReservationAttemptsByAdvertisement(String advertisementId);

    /**
     * Creates a new reservation attempt
     *
     * @param reservationAttemptDTO the reservation DTO containing the details of the new request
     * @return the created request response data transfer object
     */
    ReservationAttemptResponseDTO createReservationAttempt(ReservationAttemptDTO reservationAttemptDTO);

    /**
     * Partially updates a reservation attempts
     *
     * @param id the ID of the reservation attempt to be partially updated
     * @param idAdvertisement  the ID of the advertisement
     * @param reservationAttemptStatusDTO   the object with the new status
     * @return the partially updated response data transfer object
     */
    ReservationAttemptResponseDTO updateReservationAttemptStatus
    (String id, String idAdvertisement, ReservationAttemptStatusDTO reservationAttemptStatusDTO);

}
