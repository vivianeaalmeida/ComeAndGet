package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upskill.springboot.DTOs.*;
import org.upskill.springboot.Exceptions.*;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.AdvertisementRepository;
import org.upskill.springboot.Services.Interfaces.IAdvertisementService;
import org.upskill.springboot.WebClient.MunicipalityWebClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing advertisements.
 */
@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;
  
    @Autowired
    private ItemService itemService;
  
    private final UserService userService;

    private final RequestService requestService;

    @Autowired
    private MunicipalityWebClient municipalityWebClient;

    @Autowired
    public AdvertisementService(@Lazy UserService userService, @Lazy RequestService requestService) {
        this.userService = userService;
        this.requestService = requestService;
    }

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return the advertisement data transfer object
     * @throws AdvertisementNotFoundException if the advertisement is not found
     */
    @Override
    public AdvertisementDTO getAdvertisementById(String id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement not found"));

        // Check if the advertisement is inactive (is treated as not found)
        if (advertisement.getStatus() == Advertisement.AdvertisementStatus.INACTIVE) {
            throw new AdvertisementNotFoundException("Advertisement not found");
        }

        AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
        advertisementDTO.setMunicipality(advertisement.getMunicipality());

        return advertisementDTO;
    }

    /**
     * Retrieves all advertisements (except inactives) with pagination.
     *
     * @param page the page number
     * @param size the size of the page
     * @return a page of advertisement data transfer objects
     */
    @Override
    public Page<AdvertisementDTO> getAllAdvertisements(int page, int size) {
        Page<Advertisement> advertisements = advertisementRepository.findAll(PageRequest.of(page, size));

        // Get dto list of all advertisements without inactives
        List<AdvertisementDTO> advertisementsDTO = advertisements.stream()
                .filter(advertisement -> advertisement.getStatus() != Advertisement.AdvertisementStatus.INACTIVE)
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());
                    return advertisementDTO;
                })
                .collect(Collectors.toList());

        // Calculate the total number of advertisements without the inactives
        long totalVisibleAdvertisements = advertisements.stream()
                .filter(advertisement -> advertisement.getStatus() != Advertisement.AdvertisementStatus.INACTIVE)
                .count();

        return new PageImpl<>(advertisementsDTO, PageRequest.of(page, size), totalVisibleAdvertisements);
    }

    /**
     * Retrieves all active advertisements with pagination.
     *
     * @param page the page number
     * @param size the size of the page
     * @return a page of active advertisement data transfer objects
     */
    @Override
    public Page<AdvertisementDTO> getActiveAdvertisements(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return advertisementRepository.findByStatus(Advertisement.AdvertisementStatus.ACTIVE, pageRequest)
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());
                    return advertisementDTO;
                });
    }

    /**
     * Retrieves all closed advertisements with pagination.
     *
     * @param page the page number
     * @param size the size of the page
     * @return a page of closed advertisement data transfer objects
     */
    @Override
    public Page<AdvertisementDTO> getClosedAdvertisements(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return advertisementRepository.findByStatus(Advertisement.AdvertisementStatus.CLOSED, pageRequest)
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());
                    return advertisementDTO;
                });
    }

    /**
     * Retrieves all advertisements by client ID with pagination.
     *
     * @param page the page number (zero-based index)
     * @param size the number of items per page
     * @param clientId the ID of the client
     * @return a page of advertisements associated with the given client ID
     */
    @Override
    public Page<AdvertisementDTO> getAdvertisementsByClientId(int page, int size, String clientId) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // Retrieve advertisements associated with the clientId
        Page<Advertisement> advertisements = advertisementRepository.findByClientId(clientId, pageRequest);

        // Filter out inactive advertisements and map the rest to DTOs
        List<AdvertisementDTO> advertisementsDTO = advertisements.getContent().stream()
                .filter(advertisement -> advertisement.getStatus() != Advertisement.AdvertisementStatus.INACTIVE) // Exclude inactive advertisements
                .map(AdvertisementMapper::toDTO) // Map the active advertisements to DTOs
                .collect(Collectors.toList());

        // Calculate the total number of advertisements without the inactives
        long totalVisibleAdvertisements = advertisements.stream()
                .filter(advertisement -> advertisement.getStatus() != Advertisement.AdvertisementStatus.INACTIVE)
                .count();

        return new PageImpl<>(advertisementsDTO, pageRequest, totalVisibleAdvertisements);
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
        validateAdvertisementCreation(advertisementDTO);

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
        advertisement.setMunicipality(advertisementDTO.getMunicipality());

        // Saves the advertisement in the database
        advertisement = advertisementRepository.save(advertisement);

        AdvertisementDTO savedAdvertisementDTO = AdvertisementMapper.toDTO(advertisement);
        savedAdvertisementDTO.setMunicipality(advertisement.getMunicipality());

        return savedAdvertisementDTO;
    }

    /**
     * Updates an existing advertisement.
     *
     * @param id                     the ID of the advertisement to update
     * @param advertisementUpdateDTO the advertisement data transfer object containing the updated details
     * @return the updated advertisement data transfer object
     */
    @Override
    @Transactional
    public AdvertisementDTO updateAdvertisement(String id, AdvertisementUpdateDTO advertisementUpdateDTO) {
        // Validates AdvertisementUpdateDTO
        validateAdvertisementUpdate(id, advertisementUpdateDTO);

        // Get advertisement
        AdvertisementDTO advertisementDTO = getAdvertisementById(id);

        // Updated advertisement
        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        advertisement.setTitle(advertisementUpdateDTO.getTitle());
        advertisement.setDescription(advertisementUpdateDTO.getDescription());

        // If status is changed to closed, rejects all requests
        if (advertisementUpdateDTO.getStatus().equalsIgnoreCase("CLOSED")) {
            requestService.rejectRequests(advertisement.getId());
            advertisement.setStatus(Advertisement.AdvertisementStatus.CLOSED);
        }

        // Saves advertisement
        advertisement = advertisementRepository.save(advertisement);

        // Converts to DTO and sets municipality
        AdvertisementDTO savedAdvertisementDTO = AdvertisementMapper.toDTO(advertisement);
        savedAdvertisementDTO.setMunicipality(advertisement.getMunicipality());

        return savedAdvertisementDTO;
    }

    /**
     * Changes the status of an advertisement to inactive.
     *
     * @param id The unique identifier of the advertisement to be updated.
     * @return The updated {@link AdvertisementDTO} with the new status set to INACTIVE.
     */
    @Override
    @Transactional
    public AdvertisementDTO patchAdvertisementStatusToInactive(String id) {
        validatePatchStatusToInactive(id);

        AdvertisementDTO advertisementDTO = getAdvertisementById(id);

        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        advertisement.setMunicipality(advertisementDTO.getMunicipality());

        // Set status to INACTIVE
        advertisement.setStatus(Advertisement.AdvertisementStatus.INACTIVE);

        // Save updated advertisement
        advertisement = advertisementRepository.save(advertisement);

        requestService.rejectRequests(advertisement.getId());

        AdvertisementDTO updatedAdvertisementDTO = AdvertisementMapper.toDTO(advertisement);
        updatedAdvertisementDTO.setMunicipality(advertisement.getMunicipality());

        return updatedAdvertisementDTO;
    }

    /**
     * Creates a new advertisement based on the provided data.
     *
     * @param id The unique identifier of the advertisement.
     * @param advertisementRequestDTO An object containing the request details for creating the advertisement.
     * @return A {@code RequestResponseDTO} object containing the response of the request.
     */
    public RequestResponseDTO createAdvertisementRequest(String id, RequestDTO advertisementRequestDTO){
        advertisementRequestDTO.setAdvertisementId(id);
        return requestService.createRequest(advertisementRequestDTO);
    }

    /**
     * Retrieves the advertisement request details based on the provided advertisement ID and request ID.
     *
     * @param idAdvertisement The unique identifier of the advertisement.
     * @param idRequest The unique identifier of the request associated with the advertisement.
     * @return A {@link RequestResponseDTO} object containing the details of the advertisement request.
     */
    public RequestResponseDTO getAdvertisementRequestById(String idAdvertisement, String idRequest){
        RequestResponseDTO response = requestService.getRequestById(idRequest);
        if(!response.getAdvertisementId().equals(idAdvertisement)){
            throw new RequestNotFoundException("Advertisement id invalid.");
        }
        return response;
    }

    /**
     * Updates the status of a request for an advertisement.
     * This method calls the service to apply the changes to the status of a specific request related to an advertisement.
     *
     * @param idAdvertisement The unique identifier of the advertisement.
     * @param idRequest The unique identifier of the request.
     * @param requestStatusDTO The object containing the new status information for the request.
     *
     * @return A {@link RequestResponseDTO} object containing the details of the operation's response.
     *
     */
    public RequestResponseDTO patchAdvertisementRequestStatus(String idAdvertisement, String idRequest, RequestStatusDTO requestStatusDTO){
        return requestService.patchRequest(idRequest, idAdvertisement, requestStatusDTO);
    }

    /**
     * Validates the advertisement data transfer object.
     *
     * @param advertisementDTO the advertisement data transfer object
     * @return true if the advertisement is valid
     * @throws AdvertisementInvalidLengthException if the title or description length is invalid
     */
    private boolean validateAdvertisementCreation(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO == null) {
            throw new IllegalArgumentException("The advertisement must be provided.");
        }

        validateTitleAndDescription(advertisementDTO.getTitle(), advertisementDTO.getDescription());
        validateAdvertisementMunicipality(advertisementDTO);

        // Check if status is active
        Advertisement.AdvertisementStatus statusEnum = Advertisement.AdvertisementStatus
                .valueOf(advertisementDTO.getStatus());

        if (statusEnum != Advertisement.AdvertisementStatus.ACTIVE) {
            throw new AdvertisementValidationException("Advertisement must have active status by default");
        }

        // Check if the client associated with the advertisement is valid
        UserDTO user = userService.getUserById(advertisementDTO.getClientId());
        if (user == null) {
            throw new ClientNotFoundException("Client client not found");
        }

        return true;
    }


    /**
     * Validates the update of an advertisement by its ID.
     * Ensures that the advertisement can be updated by checking its existence,
     * status and associated requests.
     *
     * @param id the ID of the advertisement to be updated
     * @return the advertisement data transfer object
     * @throws AdvertisementNotFoundException      if the advertisement does not exist
     * @throws AdvertisementInvalidActionException if the advertisement is not active or is closed
     */
    private boolean validateAdvertisementUpdate(String id, AdvertisementUpdateDTO advertisementUpdateDTO) {
        AdvertisementDTO advertisementDTO = this.getAdvertisementById(id);
        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        validateTitleAndDescription(advertisementUpdateDTO.getTitle(), advertisementUpdateDTO.getDescription());

        // Check if the advertisement has requests. If so, the advertisement cannot be updated
        if (requestService.hasRequestsInAdvertisement(advertisement.getId())) {
            throw new AdvertisementInvalidActionException("The advertisement with id " + id +
                    " has requests, therefore it cannot be updated.");
        }

        // Check if the advertisement is closed. If so, the advertisement cannot be updated
        if (advertisement.getStatus().equals(Advertisement.AdvertisementStatus.CLOSED)) {
            throw new AdvertisementInvalidActionException("The advertisement with id " + id + " is closed, therefore " +
                    "it cannot be updated.");
        }

        // Validates the status
        if (!advertisementUpdateDTO.getStatus().equalsIgnoreCase("ACTIVE") &&
                !advertisementUpdateDTO.getStatus().equalsIgnoreCase("CLOSED")) {
            throw new AdvertisementInvalidActionException("Invalid status. Only ACTIVE or CLOSED statuses are allowed.");
        }

        return true;
    }

    private boolean validateTitleAndDescription(String title, String description) {
        // Check if the title is less than 5 or more than 50 characters
        if (title.length() < 5 || title.length() > 50) {
            throw new AdvertisementValidationException("The title must have between 5 and 50 characters.");
        }

        // Check if the description is less than 5 or more than 50 characters
        if (description.length() < 5 || description.length() > 50) {
            throw new AdvertisementValidationException("The description must have between 5 and 50 characters.");
        }

        return true;
    }

    /**
     * Validates the change of status of an advertisement to INACTIVE.
     * Ensures that the advertisement can be updated by checking its existence,
     * status, and associated requests.
     *
     * @param id the ID of the advertisement to be updated
     * @return the advertisement data transfer object
     * @throws AdvertisementNotFoundException      if the advertisement does not exist
     * @throws AdvertisementInvalidActionException if the advertisement is already inactive or has one request with the status donated
     */
    private boolean validatePatchStatusToInactive(String id) {
        // Fetch the advertisement from the repository by its ID
        Advertisement advertisement = this.advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement with id " + id + " not found"));

        if (advertisement.getStatus() == Advertisement.AdvertisementStatus.INACTIVE) {
            throw new AdvertisementInvalidActionException("Advertisement is already inactive");
        }

        // Check if there are any requests with status 'DONATED' associated with this advertisement
        if (requestService.hasDonatedRequestInAdvertisement(advertisement.getId())) {
            throw new AdvertisementInvalidActionException("Cannot change the status of the advertisement with donated requests.");
        }

        return true;
    }

    /**
     * Method to validate the advertisement municipality
     * @param advertisementDTO the advertisement DTO
     * @return true if municipality exists
     */
    private boolean validateAdvertisementMunicipality(AdvertisementDTO advertisementDTO) {
        String municipalityName = advertisementDTO.getMunicipality();
        System.out.println(municipalityName);
        try {
            MunicipalityDTO municipalityDTO = municipalityWebClient.getMunicipalityByDesignation(municipalityName);
            advertisementDTO.setMunicipality(municipalityDTO.getDesignation());

        } catch (MunicipalityNotFound e) {
            throw new AdvertisementValidationException("Municipality not found");
        }
        return true;
    }
}