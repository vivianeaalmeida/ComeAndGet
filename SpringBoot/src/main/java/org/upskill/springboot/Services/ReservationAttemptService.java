package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;
import org.upskill.springboot.Exceptions.AdvertisementValidationException;
import org.upskill.springboot.Exceptions.ReservationAttemptNotFoundException;
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
     * @return the created RequestDTO object
     * @throws AdvertisementValidationException if the advertisement associated with the request is not active
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


    @Override
    public ReservationAttemptResponseDTO updateReservationAttemptStatus(String id, String authorization, ReservationAttemptStatusDTO reservationAttemptStatusDTO) {
        if (reservationAttemptStatusDTO.getStatus() != null) {
            Optional<ReservationAttempt> requestOpt = reservationAttemptRepository.findById(id);
            if (requestOpt.isPresent()) {
                ReservationAttempt reservationAttempt = requestOpt.get();
                String clientId = userWebClient.getUserId(authorization);
                ReservationAttempt.ReservationAttemptStatus newStatus = ReservationAttempt.ReservationAttemptStatus.valueOf(reservationAttemptStatusDTO.getStatus().toUpperCase());
                validateUpdateStatus(reservationAttempt, clientId, newStatus);
                reservationAttempt.setStatus(newStatus);
                boolean isNewStatusAccepted = newStatus.equals(ReservationAttempt.ReservationAttemptStatus.ACCEPTED);
                if(isNewStatusAccepted) {
                    closeAdvertisementAndRejectedOtherAttempts(id, reservationAttempt);

                }
                return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
            } else {
                throw new ReservationAttemptNotFoundException("Request not found with id: " + id);
            }
        } else {
            throw new IllegalStateException("Status cannot be null.");
        }
    }

    private void closeAdvertisementAndRejectedOtherAttempts(String id, ReservationAttempt reservationAttempt) {
        advertisementService.closeAdvertisement(reservationAttempt.getAdvertisement().getId());
        List<ReservationAttempt> allAttemptsByAdvertisement = reservationAttemptRepository.findByAdvertisement_Id(reservationAttempt.getAdvertisement().getId());
        for (ReservationAttempt attempt : allAttemptsByAdvertisement) {
            boolean isTheAcceptedRequest = attempt.getId().equals(id);
            if (!isTheAcceptedRequest) {
                attempt.setStatus(ReservationAttempt.ReservationAttemptStatus.REJECTED);
                reservationAttemptRepository.save(attempt);
            }
        }
    }

    private void validateUpdateStatus(ReservationAttempt reservationAttempt, String clientId, ReservationAttempt.ReservationAttemptStatus newStatus) {
        boolean isAdvertisementClosed = reservationAttempt.getAdvertisement().getStatus().equals(Advertisement.AdvertisementStatus.CLOSED);
        boolean isAdvertisementInactive = reservationAttempt.getAdvertisement().getStatus().equals(Advertisement.AdvertisementStatus.INACTIVE);
        if(isAdvertisementClosed || isAdvertisementInactive) {
            throw new IllegalStateException("The advertisement is closed or inactive so the reservation attempt cannot be updated.");
        }
        if(unmodifiableStatus.contains(reservationAttempt.getStatus())){
            throw new IllegalStateException("The newStatus of the reservation cannot be updated.");
        }

        boolean isReservationAttemptOwner = clientId.equals(reservationAttempt.getClientId());
        boolean isAdvertisementOwner = clientId.equals(reservationAttempt.getAdvertisement().getClientId());
        if(isAdvertisementOwner){
            boolean isNewStatusAccepted = newStatus.equals(ReservationAttempt.ReservationAttemptStatus.ACCEPTED);
            boolean isNewStatusRejected = newStatus.equals(ReservationAttempt.ReservationAttemptStatus.REJECTED);
            if (!isNewStatusAccepted && !isNewStatusRejected){
                throw new IllegalStateException("Unauthorized change.");
            }
        } else if (isReservationAttemptOwner) {
            boolean isNewStatusPending = newStatus.equals(ReservationAttempt.ReservationAttemptStatus.PENDING);
            boolean isNewStatusCanceled = newStatus.equals(ReservationAttempt.ReservationAttemptStatus.CANCELED);
            if(!isNewStatusPending && !isNewStatusCanceled){
                throw new IllegalStateException("Unauthorized change.");
            }
        } else {
            throw new IllegalStateException("Only the request owner or the advertisement owner can update the newStatus.");
        }
    }

    private boolean validateReservationAttempt(ReservationAttemptDTO reservationAttemptDTO, AdvertisementDTO advertisementDTO, String loggedClientId) {
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
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findByAdvertisementIdAndStatusIn(
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
