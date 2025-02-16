package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.AdvertisementDTO;

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
     * @param advertisementDto   the advertisement data transfer object containing the updated details
     * @return the updated advertisement data transfer object
     */
    AdvertisementDTO updateAdvertisement(String id, AdvertisementDTO advertisementDto);

    /**
     * Deletes an advertisement by its ID.
     *
     * @param id the ID of the advertisement to be deleted
     * @return the deleted advertisement data transfer object
     */
    AdvertisementDTO deleteAdvertisement(String id);
}