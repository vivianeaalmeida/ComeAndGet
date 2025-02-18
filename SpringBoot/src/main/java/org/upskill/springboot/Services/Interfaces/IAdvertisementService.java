package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.*;

import java.util.Optional;

/**
 * Service interface for managing advertisements.
 */
public interface IAdvertisementService {
    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return the advertisement data transfer object corresponding to the given ID
     */
    AdvertisementDTO getAdvertisementById(String id);

    /**
     * Retrieves a paginated list of all advertisements.
     *
     * @param page the page number (zero-based index)
     * @param size the number of items per page
     * @return a page of all advertisements
     */
    Page<AdvertisementDTO> getAllAdvertisements(int page, int size);

    /**
     * Retrieves a paginated list of active advertisements.
     *
     * @param page the page number (zero-based index)
     * @param size the number of items per page
     * @return a page of active advertisements
     */
    Page<AdvertisementDTO> getActiveAdvertisements(int page, int size);

    /**
     * Retrieves a paginated list of closed advertisements.
     *
     * @param page the page number (zero-based index)
     * @param size the number of items per page
     * @return a page of closed advertisements
     */
    Page<AdvertisementDTO> getClosedAdvertisements(int page, int size);

    /**
     * Retrieves a paginated list of advertisements by client ID.
     *
     * @param page the page number (zero-based index)
     * @param size the number of items per page
     * @param id the ID of the user
     * @return a page of advertisements associated with the given user ID
     */
    Page<AdvertisementDTO> getAdvertisementsByClientId(int page, int size, String id);

    /**
     * Creates a new advertisement.
     *
     * @param advertisementDTO the advertisement data transfer object containing the details of the advertisement
     * @return the created advertisement data transfer object
     */
    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO);


    /**
     * Updates an existing advertisement.
     *
     * @param id                 the ID of the advertisement to be updated
     * @param advertisementUpdateDTO   the advertisement data transfer object containing the updated details
     * @return the updated advertisement data transfer object
     */
    AdvertisementDTO updateAdvertisement(String id, AdvertisementUpdateDTO advertisementUpdateDTO);

    /**
     * Changes the status of an advertisement to inactive.
     *
     * @param id The unique identifier of the advertisement to be updated.
     * @return The updated {@link AdvertisementDTO} with the new status.
     */
    AdvertisementDTO deactivateAdvertisement(String id);


    /**
     * Creates a new advertisement based on the provided data.
     *
     * @param id The unique identifier of the advertisement.
     * @param advertisementReservationAttemptDTO An object containing the request details for creating the advertisement.
     * @return A {@code RequestResponseDTO} object containing the response of the request.
     */
    ReservationAttemptResponseDTO createAdvertisementReservationAttempt(String id, ReservationAttemptDTO advertisementReservationAttemptDTO);

    /**
     * Retrieves the advertisement request details based on the provided advertisement ID and request ID.
     *
     * @param idAdvertisement The unique identifier of the advertisement.
     * @param idRequest The unique identifier of the request associated with the advertisement.
     * @return A {@link ReservationAttemptResponseDTO} object containing the details of the advertisement request.
     */
    ReservationAttemptResponseDTO getAdvertisementRequestById(String idAdvertisement, String idRequest);

    /**
     * Updates the status of a request for an advertisement.
     * This method calls the service to apply the changes to the status of a specific request related to an advertisement.
     *
     * @param idAdvertisement The unique identifier of the advertisement.
     * @param idRequest The unique identifier of the request.
     * @param reservationAttemptStatusDTO The object containing the new status information for the request.
     *
     * @return A {@link ReservationAttemptResponseDTO} object containing the details of the operation's response.
     *
     */

    ReservationAttemptResponseDTO patchAdvertisementRequestStatus(String idAdvertisement, String idRequest, ReservationAttemptStatusDTO reservationAttemptStatusDTO);

    /*Page<AdvertisementDTO> searchAdvertisements(int page, int size, Optional<String> municipality, Optional<String> keyword, Optional<String> category);*/
}