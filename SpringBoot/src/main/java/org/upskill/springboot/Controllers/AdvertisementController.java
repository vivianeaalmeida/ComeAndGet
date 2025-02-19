package org.upskill.springboot.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.upskill.springboot.DTOs.*;
import org.upskill.springboot.Services.AdvertisementService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing advertisements.
 */
@RestController
@CrossOrigin("*")
public class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return A ResponseEntity containing a list of AdvertisementDTO
     *
     */
    @GetMapping("/advertisements/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisementById(@PathVariable String id) {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(id);
        return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
    }

    /**
     * Retrieves all advertisements except inactive ones
     *
     * @return A ResponseEntity containing a list of AdvertisementDTO
     */
    @GetMapping("/advertisements")
    public ResponseEntity<List<AdvertisementDTO>> getAdvertisements() {
        List<AdvertisementDTO> advertisementsDTO = advertisementService.getAdvertisements();
        return new ResponseEntity<>(advertisementsDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a list of active advertisements.
     *
     * @return A ResponseEntity containing a list of AdvertisementDTO with active status
     */
    @GetMapping("/advertisements/active")
    public ResponseEntity<List<AdvertisementDTO>> getActiveAdvertisements() {
        List<AdvertisementDTO> advertisementsDTO = advertisementService.getActiveAdvertisements();
        return new ResponseEntity<>(advertisementsDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a list of closed advertisements.
     *
     * @return A ResponseEntity containing a list of AdvertisementDTO with closed status
     */
    @GetMapping("/advertisements/closed")
    public ResponseEntity<List<AdvertisementDTO>> getClosedAdvertisements() {
        List<AdvertisementDTO> advertisementsDTO = advertisementService.getClosedAdvertisements();
        return new ResponseEntity<>(advertisementsDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a list of all advertisements (except inactive ones) by client ID.
     *
     * @param clientId the ID of the client
     * @return A ResponseEntity containing a list of a AdvertisementDTO of the client
     */
    @GetMapping("users/{clientId}/advertisements")
    public ResponseEntity<List<AdvertisementDTO>> getAdvertisementsByClientId(
            @PathVariable String clientId) {

        List<AdvertisementDTO> advertisementsDTO = advertisementService
                .getAdvertisementsByClientId(clientId);

        return new ResponseEntity<>(advertisementsDTO, HttpStatus.OK);
    }

    /**
     * Creates a new advertisement.
     *
     * @param request the advertisement data transfer object
     * @return the created advertisement data transfer object with HTTP status CREATED
     */
    @PostMapping(value = "/advertisements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdvertisementDTO> createAdvertisement(
            @RequestPart(value = "advertisementDTO", required = true) @Valid AdvertisementDTO request,
            @RequestPart(value = "imageFile", required = true) MultipartFile imageFile) {

        // Calls service to create advertisement and saves the image
        AdvertisementDTO advertisementDTO;
        try {
            advertisementDTO = advertisementService.createAdvertisement(request, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(advertisementDTO);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error processing image", e);
        }
    }

    /**
     * Updates an existing advertisement by its ID.
     *
     * @param id The unique identifier of the advertisement to be updated.
     * @param request The AdvertisementUpdateDTO object containing the advertisement data to be updated.
     * @return A ResponseEntity containing the updated AdvertisementDTO with a self HATEOAS link
     * and HTTP status 200 (Ok) if the update is successful.
     */
    @PutMapping("/advertisements/{id}")
    public ResponseEntity<AdvertisementDTO> updateAdvertisement(@PathVariable("id") String id,
                                                                @RequestBody AdvertisementUpdateDTO request)
    {
        if (!id.equals(request.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AdvertisementDTO advertisementDTO = advertisementService.updateAdvertisement(id, request);

        return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
    }

    /**
     * Deactivates an advertisement by updating the status of an advertisement to INACTIVE.
     *
     * @param id The ID of the advertisement to be deactivated.
     * @return  A ResponseEntity containing the deactivated AdvertisementDTO
     */
    @PatchMapping("/advertisements/{id}/deactivate")
    public ResponseEntity<AdvertisementDTO> deactivateAdvertisement(@PathVariable String id) {
        AdvertisementDTO advertisementDTO = advertisementService.deactivateAdvertisement(id);
        return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
    }

    /**
     * Create a new advertisement request.
     *
     * @param id The unique identifier of the advertisement.
     * @param reservationAttemptDTO The request data for creating the advertisement request.
     * @return A ResponseEntity containing the response data.
     */
    @PostMapping("/advertisements/{id}/reservationAttempts")
    public ResponseEntity<ReservationAttemptResponseDTO> createAdvertisementRequest(@PathVariable String id, @RequestBody ReservationAttemptDTO reservationAttemptDTO) {
        ReservationAttemptResponseDTO response = advertisementService.createAdvertisementReservationAttempt(id, reservationAttemptDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieve an advertisement request by its advertisement ID and request ID.
     *
     * @param adId The unique identifier of the advertisement.
     * @param reservationId The unique identifier of the request associated with the advertisement.
     * @return A {ResponseEntity containing the  ReservationAttemptResponseDTO with the advertisement request details,
     *         and an HTTP status of 200 OK if the request is successful.
     */
    @GetMapping("/advertisements/{adId}/reservationAttempts/{reservationId}")
    public ResponseEntity<ReservationAttemptResponseDTO> getAdvertisementReservationAttemptById(@PathVariable String adId, @PathVariable String reservationId) {
        ReservationAttemptResponseDTO response = advertisementService.getAdvertisementRequestById(adId, reservationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of a reservation request for a specific advertisement.
     * This method handles a PATCH request and invokes the service to update the reservation request status for the specified advertisement.
     *
     * @param adId The unique identifier of the advertisement.
     * @param reservationId The unique identifier of the reservation request.
     * @param requestDTO The object containing the new status information for the reservation request.
     *
     * @return A ResponseEntity containing a ReservationAttemptResponseDTO object with the details of the operation's response, including the HTTP status.
     */
    @PatchMapping("/advertisements/{adId}/reservationAttempts/{reservationId}/status")
    public ResponseEntity<ReservationAttemptResponseDTO> patchReservationAttemptStatus(@PathVariable String adId, @PathVariable String reservationId, @RequestBody ReservationAttemptStatusDTO requestDTO) {
        ReservationAttemptResponseDTO response = advertisementService.patchAdvertisementRequestStatus(adId, reservationId , requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Retrieves a list active advertisements based on search filters
     * This method allows the user to search for active advertisements by applying filters such as keyword, municipality, and category
     *
     * @param keyword A keyword to search advertisements whose title or description contain the specified text (optional).
     * @param municipality The municipality to filter the advertisements (optional).
     * @param category The category to filter the advertisements (optional).
     *
     * @return A ResponseEntity containing a list of AdvertisementDTO that match the provided criteria
     *         The HTTP status returned will be 200 OK if the operation is successful.
     *
     */
    @GetMapping("/advertisements/active/search")
    public ResponseEntity<List<AdvertisementDTO>> searchAdvertisements(
            @RequestParam Optional<String> keyword,
            @RequestParam Optional<String> municipality,
            @RequestParam Optional<String> category
    ) {

        List<AdvertisementDTO> advertisementsDTO = advertisementService.searchAdvertisements(
                municipality.orElse(null),
                keyword.orElse(null),
                category.orElse(null)
        );

        return new ResponseEntity<>(advertisementsDTO, HttpStatus.OK);
    }
}
