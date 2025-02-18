package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;

/**
 * Service interface for managing requests.
 */
public interface IReservationAttemptService {

    /**
     * Retrieves all requests.
     *
     * @return a list of all request response data transfer objects
     */
    Page<ReservationAttemptResponseDTO> getReservationAttempts(int page, int size);

    /**
     * Retrieves a specific request by its ID.
     *
     * @param id the ID of the request
     * @return the request response data transfer object corresponding to the given ID
     */
    ReservationAttemptResponseDTO getReservationAttemptById(String id);

    /**
     * Creates a new request.
     *
     * @param reservationAttemptDTO the request data transfer object containing the details of the new request
     * @return the created request response data transfer object
     */
    ReservationAttemptResponseDTO createReservationAttempt(ReservationAttemptDTO reservationAttemptDTO);

    /**
     * Updates an existing request.
     *
     * @param id           the ID of the request to be updated
     * @param reservationAttemptDTO   the request data transfer object containing the updated details
     * @return the updated request response data transfer object
     */
    ReservationAttemptResponseDTO updateReservationAttempt(String id, ReservationAttemptDTO reservationAttemptDTO);

    /**
     * Partially updates a request.
     *
     * @param id           the ID of the request to be partially updated
     * @param idAdvertisement  the ID of the advertisement
     * @param reservationAttemptStatusDTO   the object with the new status
     * @return the partially updated request response data transfer object
     */
    ReservationAttemptResponseDTO patchReservationAttempt(String id, String idAdvertisement, ReservationAttemptStatusDTO reservationAttemptStatusDTO);

    /**
     * Deletes a request by its ID.
     *
     * @param id the ID of the request to be deleted
     */
    void deleteReservationAttempt(String id);

}
