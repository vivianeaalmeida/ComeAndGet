package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * Fetches a list of reservation attempts based on the provided filter criteria.
     * If no filters are specified, all reservation attempts are returned.
     *
     * @param reservationAttemptClientId (Optional) The client ID linked to the reservation attempt.
     * @param advertisementClientId (Optional) The client ID linked to the advertisement.
     * @param advertisementId (Optional) The unique identifier of the advertisement.
     * @return A ResponseEntity containing a list of reservation attempt DTOs and an HTTP 200 (OK) status if successful.
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
     * Creates a new reservation attempt.
     *
     * @param reservationAttemptDTO the ReservationAttemptDTO object containing the reservation attempt data
     * @return a ResponseEntity containing the created ReservationAttemptResponseDTO and HTTP status OK if creation is successful
     */
    @PostMapping("/reservationAttempts")
    @PreAuthorize("hasRole('User')")
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
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ReservationAttemptResponseDTO> patchReservationAttemptStatus(
            @PathVariable String reservationId, @RequestHeader("Authorization")
            String authorization,
            @RequestBody ReservationAttemptStatusDTO requestDTO) {
        ReservationAttemptResponseDTO responseDTO = reservationAttemptService.updateReservationAttemptStatus(
                reservationId, authorization, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
