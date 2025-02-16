package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Exceptions.ClientNotFoundException;
import org.upskill.springboot.Exceptions.InvalidLengthException;
import org.upskill.springboot.Exceptions.NotFoundException;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.AdvertisementRepository;
import org.upskill.springboot.Services.Interfaces.IAdvertisementService;

import java.time.LocalDate;

/**
 * Service class for managing advertisements.
 */
@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;


    /**
     * Retrieves all advertisements with pagination.
     *
     * @param page the page number
     * @param size the size of the page
     * @return a page of advertisement data transfer objects
     */
    @Override
    public Page<AdvertisementDTO> getAllAdvertisements(int page, int size) {
        return advertisementRepository.findAll(PageRequest.of(page, size))
                .map(AdvertisementMapper::toDTO);

    }

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return the advertisement data transfer object
     * @throws NotFoundException if the advertisement is not found
     */
    @Override
    public AdvertisementDTO getAdvertisementById(String id) {
        return advertisementRepository.findById(id)
                .map(AdvertisementMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Advertisement not found"));
    }

    /**
     * Creates a new advertisement.
     *
     * @param advertisementDTO the advertisement data transfer object
     * @return the created advertisement data transfer object
     */
    @Override
    @Transactional
    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO) {
        // Validates the advertisement data
        validateAdvertisement(advertisementDTO);

        // Validates the ItemDTO
        ItemDTO itemDTO = advertisementDTO.getItem();
        itemService.validateItem(itemDTO);

        // Creates the item and returns the ItemDTO after is saved in the DB
        ItemDTO savedItemDTO = itemService.createItem(itemDTO);

        // Converts the ItemDTO back to the Item entity
        Item item = ItemMapper.toEntity(savedItemDTO);  // Converts to Item entity

        // Converts the AdvertisementDTO to the Advertisement entity and associates the item
        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        advertisement.setItem(item);

        // Saves the advertisement in the database
        advertisement = advertisementRepository.save(advertisement);

        // Returns the AdvertisementDTO
        return AdvertisementMapper.toDTO(advertisement);
    }

    /**
     * Updates an existing advertisement.
     *
     * @param id               the ID of the advertisement to update
     * @param advertisementDto the updated advertisement data transfer object
     * @return the updated advertisement data transfer object
     */
    @Override
    public AdvertisementDTO updateAdvertisement(String id, AdvertisementDTO advertisementDto) {
        return null;
    }

    /**
     * Deletes an advertisement by its ID.
     *
     * @param id the ID of the advertisement to delete
     * @return the deleted advertisement data transfer object
     */
    @Override
    public AdvertisementDTO deleteAdvertisement(String id) {
        return null;
    }


    /**
     * Validates the advertisement data transfer object.
     *
     * @param advertisementDTO the advertisement data transfer object
     * @return true if the advertisement is valid
     * @throws InvalidLengthException if the title or description length is invalid
     */
    public boolean validateAdvertisement(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO == null) {
            throw new IllegalArgumentException("The advertisement must be provided.");
        }

        // Check if the title is less than 5 or more than 50 characters
        if (advertisementDTO.getTitle().length() < 5 || advertisementDTO.getTitle().length() > 50) {
            throw new InvalidLengthException("The title must have between 5 and 50 characters.");
        }

        // Check if the title is less than 5 or more than 50 characters
        if (advertisementDTO.getDescription().length() < 5 || advertisementDTO.getDescription().length() > 50) {
            throw new InvalidLengthException("The description must have between 5 and 50 characters.");
        }

        // Check if the client associated with the advertisement is valid
        UserDTO user = userService.getUserById(advertisementDTO.getClientId());
        if (user == null) {
            throw new ClientNotFoundException("Client client not found");
        }

        return true;
    }
}
