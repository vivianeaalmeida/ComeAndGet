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
    public ResponseEntity<ReservationAttemptResponseDTO> postReservationAttempt(
            @RequestBody ReservationAttemptDTO reservationAttemptDTO,
            @RequestHeader("Authorization") String authorization
    ) {
        ReservationAttemptResponseDTO responseDTO = reservationAttemptService.createReservationAttempt(reservationAttemptDTO, authorization);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("reservationAttempts/{reservationId}/status")
    public ResponseEntity<ReservationAttemptResponseDTO> patchReservationAttemptStatus(@PathVariable String reservationId, @RequestBody ReservationAttemptStatusDTO requestDTO) {
        ReservationAttemptResponseDTO response = reservationAttemptService.updateReservationAttemptStatus( reservationId , requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*metodos que vieram do userController*/
    /*
    @GetMapping("/users/{userId}/reservationAttempts")
    public ResponseEntity<List<ReservationAttemptResponseDTO>> getReservationAttemptsByUserId(@PathVariable String userId) {
        List<ReservationAttemptResponseDTO> response = this.userService.getReservationAttemptByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/advertisements/reservationAttempt")
    public ResponseEntity<List<ReservationAttemptResponseDTO>> getAdvertisementReservationAttemptsByUserId(@PathVariable String userId){

        List<ReservationAttemptResponseDTO> response = userService.getReservationAttemptFromAdvertisementOfUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

}
