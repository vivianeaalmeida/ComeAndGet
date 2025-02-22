package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;
import org.upskill.springboot.Exceptions.AdvertisementValidationException;
import org.upskill.springboot.Exceptions.ReservationAttemptNotFoundException;
import org.upskill.springboot.Exceptions.UserUnauthorizedException;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.ReservationAttemptMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.ReservationAttempt;
import org.upskill.springboot.Repositories.ReservationAttemptRepository;
import org.upskill.springboot.Services.Interfaces.IReservationAttemptService;
import org.upskill.springboot.WebClient.AuthUserWebClient;

import java.util.ArrayList;
import java.util.Arrays;
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
    private AuthUserWebClient userWebClient;

    private final List<ReservationAttempt.ReservationAttemptStatus> unmodifiableStatus = Arrays.asList(
            ReservationAttempt.ReservationAttemptStatus.DONATED,
            ReservationAttempt.ReservationAttemptStatus.CANCELED,
            ReservationAttempt.ReservationAttemptStatus.REJECTED
    );

    /**
     * Retrieves all requests from the database.
     *
     * @return a list of RequestDTO objects representing all requests
     */
    @Override
    public List<ReservationAttemptResponseDTO> getReservationAttempts(String reservationAttemptClientId,
                                                                      String advertisementClientId,
                                                                      String advertisementId) {
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findAllBy(reservationAttemptClientId,advertisementClientId,advertisementId);
        List<ReservationAttemptResponseDTO> reservationAttemptResponseDTOS = new ArrayList<>();
        for (ReservationAttempt reservationAttempt : reservationAttempts) {
            reservationAttemptResponseDTOS.add(ReservationAttemptMapper.toDTO(reservationAttempt)) ;
        }
        return reservationAttemptResponseDTOS;
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
     * @param authorization the authorization of the client creating the reservation attempt
     * @return the created RequestDTO object
     */
    @Override
    public ReservationAttemptResponseDTO createReservationAttempt(ReservationAttemptDTO reservationAttemptDTO, String authorization) {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(reservationAttemptDTO.getAdvertisementId());
        String clientId = userWebClient.getUserId(authorization);
        validateReservationAttempt(reservationAttemptDTO, advertisementDTO, clientId);
        Advertisement adEntity = AdvertisementMapper.toEntity(advertisementDTO);
        ReservationAttempt reservationAttempt = ReservationAttemptMapper.toEntity(
                reservationAttemptDTO.getStatus(), clientId, adEntity
        );

        return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
    }

    /**
     * Updates the status of an existing reservation attempt with the provided status.
     * The method performs validations before changing the status and handles the logic for closing the advertisement
     * and rejecting other reservation attempts
     *
     * @param id the ID of the reservation attempt to be updated
     * @param authorization the authorization of the client making the update
     * @param reservationAttemptStatusDTO the DTO object containing the new status for the reservation attempt
     * @return the ReservationAttemptResponseDTO object with the details of the reservation attempt after the update
     */
    @Override
    public ReservationAttemptResponseDTO updateReservationAttemptStatus(String id, String authorization,
                                                                        ReservationAttemptStatusDTO reservationAttemptStatusDTO) {
        if (reservationAttemptStatusDTO.getStatus() != null) {
            Optional<ReservationAttempt> requestOpt = reservationAttemptRepository.findById(id);
            // if reservation attempt exists
            if (requestOpt.isPresent()) {
                ReservationAttempt reservationAttempt = requestOpt.get();
                String clientId = userWebClient.getUserId(authorization);

                ReservationAttempt.ReservationAttemptStatus newStatus = ReservationAttempt.ReservationAttemptStatus
                        .valueOf(reservationAttemptStatusDTO.getStatus().toUpperCase());

                // Validate if the status update is allowed based on the current reservation attempt state
                validateUpdateStatus(reservationAttempt, clientId, newStatus);

                // Update the reservation attempt with the new status
                reservationAttempt.setStatus(newStatus);

                // Check if the new status is DONATED
                boolean isNewStatusDonated = newStatus.equals(ReservationAttempt.ReservationAttemptStatus.DONATED);

                // Close the advertisement and reject other pending or accepted reservation attempts
                if (isNewStatusDonated) {
                    closeAdvertisementAndRejectedOtherAttempts(id, reservationAttempt);
                }
                return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
            } else {
                throw new ReservationAttemptNotFoundException("Reservation attempt not found with id: " + id);
            }
        } else {
            throw new IllegalStateException("Status cannot be null.");
        }
    }

    /**
     * Closes the advertisement associated with the given reservation attempt and rejects
     * all other pending or accepted reservation attempts for the same advertisement.
     *
     * @param id the ID of the reservation attempt that was marked as "DONATED"
     * @param reservationAttempt the reservation attempt that triggered this process
     */
    private void closeAdvertisementAndRejectedOtherAttempts(String id, ReservationAttempt reservationAttempt) {
        String advertisementId = reservationAttempt.getAdvertisement().getId();

        // Close the advertisement to prevent further reservations
        advertisementService.closeAdvertisement(advertisementId);

        // Find all reservation attempts related to this advertisement that are still PENDING or ACCEPTED
        List<ReservationAttempt> reservationAttemptsToReject = reservationAttemptRepository
                .findByAdvertisement_IdAndStatusIn(
                advertisementId, List.of(ReservationAttempt.ReservationAttemptStatus.PENDING
                                , ReservationAttempt.ReservationAttemptStatus.ACCEPTED)
        );

        // Reject all reservations in the list and save them in the DB
        for (ReservationAttempt attempt : reservationAttemptsToReject) {
            boolean isTheRequestDonated = attempt.getId().equals(id);
            if (!isTheRequestDonated) {
                attempt.setStatus(ReservationAttempt.ReservationAttemptStatus.REJECTED);
                reservationAttemptRepository.save(attempt);
            }
        }
    }

    /**
     * Validates whether a user is authorized to update the status of a reservation attempt.
     *
     * The update is only allowed if the advertisement is active and the current status is modifiable.
     * Only the advertisement owner or the reservation attempt owner can update the status.
     * The advertisement owner can change it to ACCEPTED, REJECTED, or DONATED, while
     * the reservation attempt owner can change it to PENDING or CANCELED.
     *
     * @param reservationAttempt The reservation attempt being updated.
     * @param clientId The ID of the client attempting to update the status.
     * @param newStatus The new status to be applied.
     */
    private void validateUpdateStatus(ReservationAttempt reservationAttempt, String clientId, ReservationAttempt.ReservationAttemptStatus newStatus) {
        // Check if the advertisement is active
        boolean isAdvertisementActive = reservationAttempt.getAdvertisement().getStatus().equals(Advertisement.AdvertisementStatus.ACTIVE);

        // If advertisement not active the reservation attempt cannot be updated
        if (!isAdvertisementActive) {
            throw new IllegalStateException("The advertisement is closed or inactive so the reservation attempt cannot be updated.");
        }

        // If the current status is unmodifiable the reservation attempt cannot be updated
        if (unmodifiableStatus.contains(reservationAttempt.getStatus())){
            throw new IllegalStateException("The newStatus of the reservation cannot be updated.");
        }

        // Determine if the user is the reservation attempt owner or the advertisement owner
        boolean isReservationAttemptOwner = clientId.equals(reservationAttempt.getClientId());
        boolean isAdvertisementOwner = clientId.equals(reservationAttempt.getAdvertisement().getClientId());

        // The advertisement owner can only change the status to ACCEPTED, REJECTED, or DONATED
        if (isAdvertisementOwner) {
            boolean isValidStatusChange = List.of(
                    ReservationAttempt.ReservationAttemptStatus.ACCEPTED,
                    ReservationAttempt.ReservationAttemptStatus.REJECTED,
                    ReservationAttempt.ReservationAttemptStatus.DONATED
            ).contains(newStatus);

            if (!isValidStatusChange) {
                throw new IllegalStateException("Unauthorized change.");
            }

        // The reservation attempt owner can only change the status to PENDING or CANCELED
        } else if (isReservationAttemptOwner) {
            boolean isValidStatusChange = List.of(
                    ReservationAttempt.ReservationAttemptStatus.PENDING,
                    ReservationAttempt.ReservationAttemptStatus.CANCELED
            ).contains(newStatus);

            if (!isValidStatusChange) {
                throw new IllegalStateException("Unauthorized change.");
            }

        // If the user is neither the advertisement or the reservation attempt owner throws exception
        } else {
            throw new UserUnauthorizedException("Only the reservation attempt owner or the advertisement owner can update the newStatus.");
        }
    }

    /**
     * Validates whether a reservation attempt can be created.
     *
     * A user cannot create a reservation attempt if they have already made a request
     * for the same advertisement, if they are the owner of the advertisement,
     * or if the advertisement is not active.
     *
     * @param reservationAttemptDTO The reservation attempt details.
     * @param advertisementDTO The advertisement details.
     * @param loggedClientId The ID of the logged-in client attempting to create the reservation.
     * @return true if the reservation attempt is valid.
     */
    private boolean validateReservationAttempt(ReservationAttemptDTO reservationAttemptDTO,
                                               AdvertisementDTO advertisementDTO,
                                               String loggedClientId) {

        if (reservationAttemptRepository.existsByAdvertisement_IdAndClientId(advertisementDTO.getId(), loggedClientId)) {
            throw new IllegalStateException("The user has already made a request for this advertisement.");
        }
        if (advertisementDTO.getClientId()!=null && advertisementDTO.getClientId().equals(loggedClientId)){
            throw new IllegalArgumentException("The user cannot create requests for their own advertisement.");
        }

        if (!advertisementDTO.getStatus().equals(Advertisement.AdvertisementStatus.ACTIVE.toString())) {
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
     * @return A list of {@link ReservationAttemptResponseDTO} objects associated with the advertisement.
     *         Returns an empty list if no reservation attempts are found.
     */
    @Override
    public List<ReservationAttemptResponseDTO> getReservationAttemptsByAdvertisement(String advertisementId) {
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findByAdvertisement_Id(advertisementId);
        List<ReservationAttemptResponseDTO> reservationAttemptResponseDTOS = new ArrayList<>();
        for (ReservationAttempt reservationAttempt : reservationAttempts) {
            reservationAttemptResponseDTOS.add(ReservationAttemptMapper.toDTO(reservationAttempt)) ;
        }
        return reservationAttemptResponseDTOS;
    }

    /**
     * Checks if there are reservation attempts associated with a specific advertisement.
     *
     * @param advertisementId The identifier of the advertisement.
     * @return {@code true} if there are reservation attempts associated with the advertisement, {@code false} otherwise.
     */
    public boolean hasDonatedRequestInAdvertisement(String advertisementId) {
        return reservationAttemptRepository.existsDonatedReservationsForAdvertisement(advertisementId);
    }

    /**
     * Rejects all reservations associated with an advertisement by its ID,
     * for reservations with status PENDING or ACCEPTED.
     *
     * @param advertisementId the ID of the advertisement
     */
    public void rejectReservationAttempts(String advertisementId) {
        // Get ReservationAttempts with pending and accepted status
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findByAdvertisement_IdAndStatusIn(
                advertisementId, List.of(ReservationAttempt.ReservationAttemptStatus.PENDING, ReservationAttempt.ReservationAttemptStatus.ACCEPTED)
        );

        //Set the reservation status to REJECTED
        for (ReservationAttempt reservationAttempt : reservationAttempts) {
            reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.REJECTED);
        }

        // Save the updated reservations
        reservationAttemptRepository.saveAll(reservationAttempts);
    }
}
