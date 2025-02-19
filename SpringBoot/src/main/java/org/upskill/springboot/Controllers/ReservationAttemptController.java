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


    @GetMapping("/reservationAttempts")
    public ResponseEntity<List<ReservationAttemptResponseDTO>> getReservationAttempts(
    ) {
        List<ReservationAttemptResponseDTO> reservationAttemptsDTO = reservationAttemptService.getReservationAttempts();
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
    public ResponseEntity<ReservationAttemptResponseDTO> postReservationAttempt(@RequestBody ReservationAttemptDTO reservationAttemptDTO) {
        ReservationAttemptResponseDTO responseDTO = reservationAttemptService.createReservationAttempt(reservationAttemptDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    /**
     * Endpoint to update the status of a reservation request for a specific advertisement.
     * This method handles a PATCH request and invokes the service to update the reservation request status for the specified advertisement.
     *
     * @param reservationId The unique identifier of the reservation request.
     * @param requestDTO The object containing the new status information for the reservation request.
     *
     * @return A {@link ResponseEntity} containing a {@link ReservationAttemptResponseDTO} object with the details of the operation's response, including the HTTP status.
     */
    @PatchMapping("reservationAttempts/{reservationId}/status")
    public ResponseEntity<ReservationAttemptResponseDTO> patchReservationAttemptStatus(@PathVariable String reservationId, @RequestBody ReservationAttemptStatusDTO requestDTO) {
        ReservationAttemptResponseDTO response = reservationAttemptService.updateReservationAttemptStatus( reservationId , requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
