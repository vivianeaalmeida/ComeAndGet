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

import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * Retrieves all requests from the database.
     *
     * @return a list of RequestDTO objects representing all requests
     */
    @Override
    public List<ReservationAttemptResponseDTO> getReservationAttempts() {
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findAll();
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
        validateReservationAttempt(reservationAttemptDTO, advertisementDTO);

        ReservationAttempt reservationAttempt = ReservationAttemptMapper.toEntity(reservationAttemptDTO);

        Advertisement adEntity = AdvertisementMapper.toEntity(advertisementDTO);

        reservationAttempt.setAdvertisement(adEntity);
        reservationAttempt.setClientId(clientId);
        reservationAttempt.setDate(LocalDate.now());

        return ReservationAttemptMapper.toDTO(reservationAttemptRepository.save(reservationAttempt));
    }

    /**
     * Partially updates the request with the provided ID with new data.
     *
     * @param id               the ID of the request to update
     * @param reservationAttemptStatusDTO the object with the new status
     * @return the partially updated RequestDTO object
     * @throws ReservationAttemptNotFoundException if the request with the given ID does not exist
     */
    @Override
    public ReservationAttemptResponseDTO updateReservationAttemptStatus(String id, ReservationAttemptStatusDTO reservationAttemptStatusDTO) {
        Optional<ReservationAttempt> requestOpt = reservationAttemptRepository.findById(id);
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

    public List<ReservationAttemptResponseDTO> getReservationAttemptByClientId(String clientId) {
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findByClientId(clientId);

        List<ReservationAttemptResponseDTO> reservationAttemptResponseDTOS = new ArrayList<>();
        for (ReservationAttempt reservationAttempt : reservationAttempts) {
            reservationAttemptResponseDTOS.add(ReservationAttemptMapper.toDTO(reservationAttempt));
        }
        return reservationAttemptResponseDTOS;
    }

    public List<ReservationAttemptResponseDTO> getReservationAttemptFromAdvertisementOfUser(String userId) {
        List<ReservationAttempt> reservationAttempts = reservationAttemptRepository.findReservationAttemptsFromAdvertisementOfUser(userId);
        List<org.upskill.springboot.DTOs.ReservationAttemptResponseDTO> reservationAttemptResponseDTOS = new ArrayList<>();
        for (ReservationAttempt reservationAttempt : reservationAttempts) {
            reservationAttemptResponseDTOS.add(ReservationAttemptMapper.toDTO(reservationAttempt)) ;
        }
        return reservationAttemptResponseDTOS;

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

    private boolean validateReservationAttempt(ReservationAttemptDTO reservationAttemptDTO, AdvertisementDTO advertisementDTO) {
        if (reservationAttemptRepository.existsByAdvertisement_IdAndClientId(advertisementDTO.getId(), reservationAttemptDTO.getUserId())) {
            throw new IllegalStateException("The user has already made a request for this advertisement.");
        }
        if (advertisementDTO.getClientId()!=null && advertisementDTO.getClientId().equals(reservationAttemptDTO.getUserId())){
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

    /*metodos do user service*/
    //chamar na interface e no endpoint
    //apagar
    /*
    @Override
    public List<ReservationAttemptResponseDTO> getReservationAttemptByUserId(String id){

        return this.reservationAttemptService.getRequestsByUserId(id);
    }

    @Override
    public List<ReservationAttemptResponseDTO> getReservationAttemptFromAdvertisementOfUser(String id) {

        return reservationAttemptService.getReservationAttemptFromAdvertisementOfUser(id);
    }
     */

}
