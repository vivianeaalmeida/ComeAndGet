package org.upskill.springboot.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.AdvertisementUpdateDTO;

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
    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO, MultipartFile imageFile, String authorization) throws IOException;


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