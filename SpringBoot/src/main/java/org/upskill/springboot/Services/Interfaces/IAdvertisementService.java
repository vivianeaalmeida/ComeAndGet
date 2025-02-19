package org.upskill.springboot.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;
import org.upskill.springboot.DTOs.*;

import java.io.IOException;
import java.util.List;

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
     * Retrieves a list of advertisements.
     *
     * @return a list of all advertisements
     */
    List<AdvertisementDTO> getAdvertisements();

    /**
     * Retrieves a list of active advertisements.
     *
     * @return a list of active advertisements
     */
    List<AdvertisementDTO> getActiveAdvertisements();

    /**
     * Retrieves a list of closed advertisements.
     *
     * @return a list of closed advertisements
     */
    List<AdvertisementDTO> getClosedAdvertisements();

    /**
     * Retrieves a list of advertisements by client ID.
     *
     * @param id the ID of the user
     * @return a list of advertisements associated with the given user ID
     */
    List<AdvertisementDTO> getAdvertisementsByClientId(String id);

    /**
     * Creates a new advertisement.
     *
     * @param advertisementDTO the advertisement data transfer object containing the details of the advertisement
     * @return the created advertisement data transfer object
     */
    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO, MultipartFile imageFile) throws IOException;


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

    /**
     * Searches for advertisements based on the provided filters and returns a list of advertisements.
     * The search includes filtering by municipality, keyword, and category.
     *
     * @param municipality filter for the municipality where the advertisement is located.
     * @param keyword keyword to search in the advertisement title or description.
     * @param category filter to search by advertisement category.
     *
     * @return a list of advertisements containing the filters or all advertisements if no filters are provided.
     */
    List<AdvertisementDTO> searchAdvertisements(String municipality, String keyword, String category);
}