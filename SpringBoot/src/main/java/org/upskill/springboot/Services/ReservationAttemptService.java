package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.*;
import org.upskill.springboot.Exceptions.AdvertisementValidationException;
import org.upskill.springboot.Exceptions.NotNullException;
import org.upskill.springboot.Exceptions.ReservationAttemptNotFoundException;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.ReservationAttemptMapper;
import org.upskill.springboot.Mappers.UserMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.ReservationAttempt;
import org.upskill.springboot.Models.User;
import org.upskill.springboot.Repositories.ReservationAttemptRepository;
import org.upskill.springboot.Services.Interfaces.IReservationAttemptService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to Request entities.
 * Provides methods for creating, updating, deleting, and retrieving Requests.
 */
@Service
public class ReservationAttemptService implements IReservationAttemptService {

    @Autowired
    private ReservationAttemptRepository reservationAttemptRepository;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all requests from the database.
     *
     * @return a list of RequestDTO objects representing all requests
     */
    @Override
    public Page<ReservationAttemptResponseDTO> getReservationAttempts(int page, int size) {
        return reservationAttemptRepository.findAll(PageRequest.of(page, size)).map(ReservationAttemptMapper::toDTO);
    }

    /**
     * Retrieves a specific request by its ID.
     *
     * @param id the ID of the request to retrieve
     * @return the RequestDTO object corresponding to the requested ID
     * @throws ReservationAttemptNotFoundException if no request is found with the given ID
     */
    @Override
    public ReservationAttemptResponseDTO getReservationAttemptById(String id) {
        ReservationAttempt reservationAttempt = reservationAttemptRepository.findById(id).orElseThrow(() -> new ReservationAttemptNotFoundException("Request not found with id: " + id));
        return ReservationAttemptMapper.toDTO(reservationAttempt);
    }

    /**
     * Creates a new request with the provided RequestDTO data.
     *
     * @param reservationAttemptDTO the RequestDTO object containing the details of the new request
     * @return the created RequestDTO object
     * @throws AdvertisementValidationException if the advertisement associated with the request is not active
     */
    @Override
    public ReservationAttemptResponseDTO createReservationAttempt(ReservationAttemptDTO reservationAttemptDTO) {
        validateRequest(reservationAttemptDTO);

        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(reservationAttemptDTO.getAdvertisementId());
        UserDTO userDTO = userService.getUserById(reservationAttemptDTO.getUserId());

        ReservationAttempt reservationAttempt = ReservationAttemptMapper.toEntity(reservationAttemptDTO);

        Advertisement adEntity = AdvertisementMapper.toEntity(advertisementDTO);

        reservationAttempt.setAdvertisement(adEntity);
        User userEntity = UserMapper.toEntity(userDTO);

        reservationAttempt.setUser(userEntity);
        reservationAttempt.setDate(LocalDate.now());

        return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
    }

    /**
     * Updates the request with the provided ID with new data.
     *
     * @param id         the ID of the request to update
     * @param reservationAttemptDTO the RequestDTO object containing the updated request data
     * @return the updated RequestDTO object
     * @throws ReservationAttemptNotFoundException if the request with the given ID does not exist
     * @throws NotNullException         if the status of the request is null
     */
    @Override
    public ReservationAttemptResponseDTO updateReservationAttempt(String id, ReservationAttemptDTO reservationAttemptDTO) {
        Optional<ReservationAttempt> requestOpt = reservationAttemptRepository.findById(id);
        if (requestOpt.isPresent()) {
            ReservationAttempt reservationAttempt = requestOpt.get();
            if (reservationAttemptDTO.getStatus() != null) {
                reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.valueOf(reservationAttemptDTO.getStatus().toUpperCase()));
            } else {
                throw new NotNullException("Status cannot be null.");
            }
            setUser(reservationAttemptDTO, reservationAttempt);
            setAdvertisement(reservationAttemptDTO, reservationAttempt);
            return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
        } else {
            throw new ReservationAttemptNotFoundException("Request not found with id: " + id);
        }
    }

    /**
     * Partially updates the request with the provided ID with new data.
     *
     * @param id               the ID of the request to update
     * @param idAdvertisement  the ID of the advertisement
     * @param reservationAttemptStatusDTO the object with the new status
     * @return the partially updated RequestDTO object
     * @throws ReservationAttemptNotFoundException if the request with the given ID does not exist
     */
    @Override
    public ReservationAttemptResponseDTO patchReservationAttempt(String id, String idAdvertisement, ReservationAttemptStatusDTO reservationAttemptStatusDTO) {
        Optional<ReservationAttempt> requestOpt = reservationAttemptRepository.findByIdAndAdvertisementId(id, idAdvertisement);
        if (requestOpt.isPresent()) {
            ReservationAttempt reservationAttempt = requestOpt.get();
            if (reservationAttemptStatusDTO.getStatus() != null) {
                reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.valueOf(reservationAttemptStatusDTO.getStatus().toUpperCase()));
            }
            return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
        } else {
            throw new ReservationAttemptNotFoundException("Request not found with id: " + id);
        }
    }

    /**
     * Deletes the request with the given ID.
     *
     * @param id the ID of the request to delete
     * @throws ReservationAttemptNotFoundException if the request with the given ID does not exist
     */
    @Override
    public void deleteReservationAttempt(String id) {
        Optional<ReservationAttempt> requestOpt = reservationAttemptRepository.findById(id);
        if (requestOpt.isPresent()) {
            reservationAttemptRepository.delete(requestOpt.get());
        } else {
            throw new ReservationAttemptNotFoundException("Request not found with id: " + id);
        }
    }

    /**
     * Retrieves a page of requests associated with a specific user ID and maps them to DTOs.
     *
     * @param userId the unique identifier of the user
     * @return a page of {@link ReservationAttemptResponseDTO} objects representing the user's requests
     */
    public Page<ReservationAttemptResponseDTO> getRequestsByUserId(String userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ReservationAttempt> requests = reservationAttemptRepository.findRequestByUser_Id(userId,pageRequest);
        return requests.map(ReservationAttemptMapper::toDTO);
    }

    /**
     * Retrieves a page of requests associated with a specific user ID and maps them to DTOs.
     *
     * @param userId the unique identifier of the user
     * @return a page of {@link ReservationAttemptResponseDTO} objects representing the user's requests
     */
    public Page<ReservationAttemptResponseDTO> getReservationAttemptFromAdvertisementOfUser(String userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ReservationAttempt> requests = reservationAttemptRepository.findRequestsFromAdvertisementOfUser(userId,pageRequest);
        return requests.map(ReservationAttemptMapper::toDTO);
    }

    /**
     * Sets the advertisement for the request using the provided RequestDTO.
     *
     * @param reservationAttemptDTO the RequestDTO object containing the advertisement ID
     * @param reservationAttempt    the Request entity to update with the advertisement
     */
    private void setAdvertisement(ReservationAttemptDTO reservationAttemptDTO, ReservationAttempt reservationAttempt) {
        AdvertisementDTO adDTO = advertisementService.getAdvertisementById(reservationAttemptDTO.getAdvertisementId());
        reservationAttempt.setAdvertisement(AdvertisementMapper.toEntity(adDTO));
    }

    /**
     * Sets the user for the request using the provided RequestDTO.
     *
     * @param reservationAttemptDTO the RequestDTO object containing the user ID
     * @param reservationAttempt    the Request entity to update with the user
     */
    private void setUser(ReservationAttemptDTO reservationAttemptDTO, ReservationAttempt reservationAttempt) {
        UserDTO userDTO = userService.getUserById(reservationAttemptDTO.getUserId());
        reservationAttempt.setUser(UserMapper.toEntity(userDTO));
    }

    /**
     * Validates the request based on various conditions such as whether the user has already made a request
     * for the advertisement, if the user is the owner of the advertisement, and if the advertisement is active.
     *
     * @param reservationAttemptDTO the {@link ReservationAttemptDTO} object containing the request details
     * @return {@code true} if the request is valid, otherwise an exception is thrown
     * @throws IllegalStateException if the user has already made a request for the advertisement
     * @throws IllegalArgumentException if the user is the owner of the advertisement
     * @throws AdvertisementValidationException if the advertisement is not active
     */
    private boolean validateRequest(ReservationAttemptDTO reservationAttemptDTO) {
        AdvertisementDTO adDTO = advertisementService.getAdvertisementById(reservationAttemptDTO.getAdvertisementId());
        if (reservationAttemptRepository.existsByAdvertisement_IdAndUser_Id(adDTO.getId(), reservationAttemptDTO.getUserId())) {
            throw new IllegalStateException("The user has already made a request for this advertisement.");
        }
        if (adDTO.getClientId().equals(reservationAttemptDTO.getUserId())) {
            throw new IllegalArgumentException("The user cannot create requests for their own advertisement.");

        }

        if (!adDTO.getStatus().equals(Advertisement.AdvertisementStatus.ACTIVE.toString())) {
            throw new AdvertisementValidationException("Advertisement is not active.");
        }
        return true;
    }

    /**
     * Checks if there are requests associated with a specific advertisement.
     *
     * @param advertisementId The identifier of the advertisement.
     * @return {@code true} if there are requests associated with the advertisement, {@code false} otherwise.
     */
    public boolean hasRequestsInAdvertisement(String advertisementId) {
        return reservationAttemptRepository.existsByAdvertisement_Id(advertisementId);
    }

    /**
     * Retrieves all requests associated with a specific advertisement.
     *
     * @param advertisementId The identifier of the advertisement.
     * @return A list of {@link ReservationAttempt} objects associated with the advertisement.
     *         Returns an empty list if no requests are found.
     */
    public List<ReservationAttempt> getRequestsByAdvertisement(String advertisementId) {
        return reservationAttemptRepository.getRequestsByAdvertisementId(advertisementId);
    }

    /**
     * Checks if there are requests associated with a specific advertisement.
     *
     * @param advertisementId The identifier of the advertisement.
     * @return {@code true} if there are requests associated with the advertisement, {@code false} otherwise.
     */
    public boolean hasDonatedRequestInAdvertisement(String advertisementId) {
        return reservationAttemptRepository.existsDonatedRequestForAdvertisement(advertisementId);
    }

    /**
     * Rejects all requests associated with an advertisement by its ID.
     *
     * @param advertisementId the ID of the advertisement
     */
    public void rejectRequests(String advertisementId) {
        List<ReservationAttempt> reservationAttempts = getRequestsByAdvertisement(advertisementId);

        //Set the request status to REJECTED
        for (ReservationAttempt reservationAttempt : reservationAttempts) {
            if (reservationAttempt.getStatus() != ReservationAttempt.ReservationAttemptStatus.CANCELED) {
                reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.REJECTED);
            }
        }

        // Save the updated requests
        reservationAttemptRepository.saveAll(reservationAttempts);
    }
}
