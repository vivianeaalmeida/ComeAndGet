package org.upskill.springboot.Services;

import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.AdvertisementUpdateDTO;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.DTOs.MunicipalityDTO;
import org.upskill.springboot.Exceptions.*;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.AdvertisementRepository;
import org.upskill.springboot.Services.Interfaces.IAdvertisementService;
import org.upskill.springboot.WebClient.AuthUserWebClient;
import org.upskill.springboot.WebClient.MunicipalityWebClient;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing advertisements.
 */
@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;
  
    @Autowired
    private ItemService itemService;

    private final ReservationAttemptService reservationAttemptService;

    @Autowired
    private MunicipalityWebClient municipalityWebClient;

    @Autowired
    private AuthUserWebClient webClient;

    @Autowired
    public AdvertisementService(@Lazy ReservationAttemptService reservationAttemptService) {
        this.reservationAttemptService = reservationAttemptService;
    }

    /**
     * Retrieves an advertisement by its ID and throws an exception if the advertisement
     * with the provided does not exist
     *
     * @param id the ID of the advertisement
     * @return the advertisement data transfer object
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

        if (advertisement.getItem() != null && advertisement.getItem().getImage() != null) {
            advertisementDTO.getItem().setImage(advertisement.getItem().getImage());
        }

        return advertisementDTO;
    }


    /**
     * Retrieves all advertisements (except inactives)
     *
     * @return a list of advertisement data transfer objects
     */
    @Override
    public List<AdvertisementDTO> getAdvertisements() {
        return advertisementRepository
                .findByStatusNot(Advertisement.AdvertisementStatus.INACTIVE)
                .stream()
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());

                    // Verifica se há um item e define a imagem corretamente
                    setItemImage(advertisement, advertisementDTO);

                    return advertisementDTO;
                })
                .toList();
    }

    /**
     * Retrieves all active advertisements
     *
     * @return a list of active advertisement data transfer objects
     */
    @Override
    public List<AdvertisementDTO> getActiveAdvertisements() {
        return advertisementRepository
                .findByStatus(Advertisement.AdvertisementStatus.ACTIVE)
                .stream()
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());
                    setItemImage(advertisement, advertisementDTO);

                    return advertisementDTO;
                })
                .toList();
    }

    /**
     * Retrieves all closed advertisements.
     *
     * @return a list of closed advertisement data transfer objects
     */
    @Override
    public List<AdvertisementDTO> getClosedAdvertisements() {
        return advertisementRepository
                .findByStatus(Advertisement.AdvertisementStatus.CLOSED)
                .stream()
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());
                    setItemImage(advertisement, advertisementDTO);

                    return advertisementDTO;
                })
                .toList();
    }
    /**
     * Retrieves all advertisements (except inactives) by client ID
     *
     * @param clientId the ID of the client
     * @return a list of advertisements associated with the given client ID
     */
    public List<AdvertisementDTO> getAdvertisementsByClientId(String clientId) {
        return advertisementRepository
                .findByClientIdAndStatusNot(clientId, Advertisement.AdvertisementStatus.INACTIVE)
                .stream()
                .map(advertisement -> {
                    AdvertisementDTO advertisementDTO = AdvertisementMapper.toDTO(advertisement);
                    advertisementDTO.setMunicipality(advertisement.getMunicipality());
                    setItemImage(advertisement, advertisementDTO);

                    return advertisementDTO;
                })
                .toList();
    }

    /**
     * Creates a new advertisement.
     *
     * @param advertisementDTO the advertisement data transfer object
     * @return the created advertisement data transfer object
     */
    @Transactional
    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO, MultipartFile imageFile, String authorization) throws IOException, java.io.IOException {
        // Obtém o ID do usuário autenticado usando o token JWT
        String clientId = webClient.getUserId(authorization);
        System.out.println("Client ID: " + clientId); // Verifique se o clientId está sendo retornado corretamente

        // Valida os dados do anúncio
        validateAdvertisementCreation(advertisementDTO);

        // Valida o ItemDTO
        ItemDTO itemDTO = advertisementDTO.getItem();
        itemService.validateItem(itemDTO);

        // Uploads the image and save the path in the DB
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = itemService.uploadItemImage(imageFile);
            itemDTO.setImage(imagePath);
        }

        // Cria o item no banco de dados
        ItemDTO savedItemDTO = itemService.createItem(itemDTO);
        Item item = ItemMapper.toEntity(savedItemDTO);

        // Converte DTO para entidade e associa o item e clientId
        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        advertisement.setItem(item);
        advertisement.setMunicipality(advertisementDTO.getMunicipality());
        advertisement.setClientId(clientId); // Agora o ID do usuário autenticado é setado corretamente
        // Salva no banco de dados
        advertisement = advertisementRepository.save(advertisement);

        // Converte para DTO e retorna
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
        advertisement.setMunicipality(advertisementDTO.getMunicipality());

        // If status is changed to closed, rejects all requests
        if (advertisement.getStatus() == Advertisement.AdvertisementStatus.CLOSED) {
            reservationAttemptService.rejectReservationAttempts(advertisement.getId());
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
    public AdvertisementDTO deactivateAdvertisement(String id) {
        validateDeactivateAdvertisement(id);

        AdvertisementDTO advertisementDTO = getAdvertisementById(id);

        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        advertisement.setMunicipality(advertisementDTO.getMunicipality());

        // Set status to INACTIVE
        advertisement.setStatus(Advertisement.AdvertisementStatus.INACTIVE);

        // Save updated advertisement
        advertisement = advertisementRepository.save(advertisement);

        reservationAttemptService.rejectReservationAttempts(advertisement.getId());

        AdvertisementDTO updatedAdvertisementDTO = AdvertisementMapper.toDTO(advertisement);
        updatedAdvertisementDTO.setMunicipality(advertisement.getMunicipality());

        return updatedAdvertisementDTO;
    }

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
    @Override
    public List<AdvertisementDTO> searchAdvertisements(String municipality, String keyword, String category){
        List<AdvertisementDTO> advertisementsDTO = advertisementRepository
                .searchAdvertisements(municipality, keyword, category)
                .stream()
                .map(advertisement -> {
                    AdvertisementDTO dto = AdvertisementMapper.toDTO(advertisement);
                    dto.setMunicipality(advertisement.getMunicipality());
                    return dto;
                })
                .toList();

        return advertisementsDTO;
    }

    /**
     * Closes the advertisement by setting the status to closed
     * @param id the id of the advertisement
     */
    public void closeAdvertisement(String id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement not found"));
        advertisement.setStatus(Advertisement.AdvertisementStatus.CLOSED);
        advertisementRepository.save(advertisement);
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
        if (advertisementDTO.getStatus() != null) {
            Advertisement.AdvertisementStatus statusEnum = Advertisement.AdvertisementStatus
                    .valueOf(advertisementDTO.getStatus());

            if (statusEnum != Advertisement.AdvertisementStatus.ACTIVE) {
                throw new AdvertisementValidationException("Advertisement must have active status by default");
            }
        }

        // Check date
        if (advertisementDTO.getDate() != null && !advertisementDTO.getDate().equals(LocalDate.now())) {
            throw new AdvertisementValidationException("Advertisement date must be the current date");
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
        if (reservationAttemptService.hasRequestsInAdvertisement(advertisement.getId())) {
            throw new AdvertisementValidationException("The advertisement with id " + id +
                    " has requests, therefore it cannot be updated.");
        }

        // Check if the advertisement is closed. If so, the advertisement cannot be updated
        if (!advertisement.getStatus().equals(Advertisement.AdvertisementStatus.ACTIVE)) {
            throw new AdvertisementValidationException("The advertisement with id " + id + " is no longer active, therefore " +
                    "it cannot be updated.");
        }

        // Validates status
        try {
            Advertisement.AdvertisementStatus status = Advertisement.AdvertisementStatus.valueOf(advertisementUpdateDTO.
                    getStatus().toUpperCase());
            if (status == Advertisement.AdvertisementStatus.INACTIVE) {
                throw new AdvertisementValidationException("Status INACTIVE is not allowed.");
            }
        } catch (IllegalArgumentException e) {
            throw new AdvertisementValidationException("Invalid status. Only ACTIVE or CLOSED status are allowed.");
        }

        return true;
    }

    /**
     * Validates the title and the description of the advertisement
     * @param title the title of the advertisement
     * @param description the description of the advertisement
     * @return true if the title and description are valid
     */
    private boolean validateTitleAndDescription(String title, String description) {
        // Check if the title is less than 5 or more than 50 characters
        if (title.length() < 5 || title.length() > 50) {
            throw new AdvertisementValidationException("The title must have between 5 and 50 characters.");
        }

        // Check if the description is less than 5 or more than 50 characters
        if (description.length() < 5 || description.length() > 200) {
            throw new AdvertisementValidationException("The description must have between 5 and 200 characters.");
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
    private boolean validateDeactivateAdvertisement(String id) {
        // Fetch the advertisement from the repository by its ID
        Advertisement advertisement = this.advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement with id " + id + " not found"));

        if (advertisement.getStatus() == Advertisement.AdvertisementStatus.INACTIVE) {
            throw new AdvertisementInvalidActionException("Advertisement is already inactive");
        }

        // Check if there are any requests with status 'DONATED' associated with this advertisement
        if (reservationAttemptService.hasDonatedRequestInAdvertisement(advertisement.getId())) {
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
        try {
            MunicipalityDTO municipalityDTO = municipalityWebClient.getMunicipalityByDesignation(municipalityName);
            advertisementDTO.setMunicipality(municipalityDTO.getDesignation());

        } catch (MunicipalityNotFound e) {
            throw new AdvertisementValidationException("Municipality not found");
        }
        return true;
    }

    /**
     * Checks if the item exists and set the item image
     * @param advertisement The advertisement object that may contain an item with an image.
     * @param advertisementDTO The AdvertisementDTO where the item image will be set, if present.
     */
    private void setItemImage(Advertisement advertisement, AdvertisementDTO advertisementDTO) {
        if (advertisement.getItem() != null) {
            advertisementDTO.getItem().setImage(advertisement.getItem().getImage());
        }
    }

    /**
     * Close expired advertisements and reject pending or accepted reservation attempts associated with them.
     * This method is scheduled to run every day at midnight.
     */
    @Scheduled(cron = "0 0 0 * * ?")  // Cron expression for every day at midnight
    public void closeExpiredAdvertisements() {
        // Get all advertisements and filter out inactive ones
        List<Advertisement> advertisements = advertisementRepository.findByStatus(Advertisement.AdvertisementStatus.ACTIVE);

        for (Advertisement advertisement : advertisements) {
            // Close advertisement if expired
            if (advertisement.closeIfExpired()) {
                advertisementRepository.save(advertisement);  // Save the advertisement with the updated status

                // Reject reservation attemps associated with the advertisement
                reservationAttemptService.rejectReservationAttempts(advertisement.getId());
            }
        }
    }
}