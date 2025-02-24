package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;
import org.upskill.springboot.Services.Interfaces.IReservationAttemptService;

import java.util.List;

/**
 * REST controller for managing reservation attempt.
 */
@RestController
public class ReservationAttemptController extends BaseController {
    @Autowired
    private IReservationAttemptService reservationAttemptService;

    /**
     * Retrieves a list of reservation attempts based on the specified filtering parameters.
     * If no parameters are provided, all reservation attempts will be returned.
     *
     * @param reservationAttemptClientId The client ID associated with the reservation attempt (optional).
     * @param advertisementClientId The client ID associated with the advertisement (optional).
     * @param advertisementId The unique identifier of the advertisement (optional).
     * @return A ResponseEntity containing a list of reservation attempt response data transfer objects
     *         and an HTTP status code of 200 (OK) if the request is successful.
     */
    @GetMapping("/reservationAttempts")
    public ResponseEntity<List<ReservationAttemptResponseDTO>> getReservationAttempts(
            @RequestParam(required = false) String reservationAttemptClientId,
            @RequestParam(required = false) String advertisementClientId,
            @RequestParam(required = false) String advertisementId
    ) {
        List<ReservationAttemptResponseDTO> reservationAttemptsDTO = reservationAttemptService.getReservationAttempts(reservationAttemptClientId, advertisementClientId, advertisementId);
        return new ResponseEntity<>(reservationAttemptsDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a reservation attempt by its ID.
     *
     * @param id the ID of the reservation attempt
     * @return a ResponseEntity containing the ReservationAttemptResponseDTO and HTTP status OK if retrieval is successful
     */
    @GetMapping("/reservationAttempts/{id}")
    public ResponseEntity<ReservationAttemptResponseDTO> getReservationAttemptById(@PathVariable String id) {
        ReservationAttemptResponseDTO responseDTO = reservationAttemptService.getReservationAttemptById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a reservation attempt by Advertisement ID.
     *
     * @param idAdvertisement the ID of the  Advertisement
     * @return a ResponseEntity containing the ReservationAttemptResponseDTO and HTTP status OK if retrieval is successful
     */
    @GetMapping("/reservationAttempts/advertisement/{idAdvertisement}")
    public ResponseEntity<List<ReservationAttemptResponseDTO>> getReservationAttemptByIdAdvertisement(@PathVariable String idAdvertisement) {
        List<ReservationAttemptResponseDTO> responseDTO = reservationAttemptService.getReservationAttemptsByAdvertisement(idAdvertisement);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Creates a new reservation attempt.
     *
     * @param reservationAttemptDTO the ReservationAttemptDTO object containing the reservation attempt data
     * @return a ResponseEntity containing the created ReservationAttemptResponseDTO and HTTP status OK if creation is successful
     */
    @PostMapping("/reservationAttempts")
    public ResponseEntity<ReservationAttemptResponseDTO> postReservationAttempt(
            @RequestBody ReservationAttemptDTO reservationAttemptDTO,
            @RequestHeader("Authorization") String authorization) {
        ReservationAttemptResponseDTO responseDTO = reservationAttemptService.createReservationAttempt(
                reservationAttemptDTO, authorization);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Updates the status of a reservation attempt.
     *
     * @param reservationId The unique identifier of the reservation attempt to be updated.
     * @param authorization The authorization token required for authentication and access control.
     * @param requestDTO The request body containing the new status of the reservation attempt.
     * @return A ResponseEntity containing the updated reservation attempt DTO and an HTTP status code of 200 (OK)
     * if successful.
     */
    @PatchMapping("/reservationAttempts/{reservationId}/status")
    public ResponseEntity<ReservationAttemptResponseDTO> patchReservationAttemptStatus(
            @PathVariable String reservationId, @RequestHeader("Authorization")
            String authorization,
            @RequestBody ReservationAttemptStatusDTO requestDTO) {
        ReservationAttemptResponseDTO responseDTO = reservationAttemptService.updateReservationAttemptStatus(
                reservationId, authorization, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
