package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.*;
import org.upskill.springboot.Services.AdvertisementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing advertisements.
 */
@RestController
public class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return the advertisement data transfer object with HTTP status OK, or HTTP status NOT FOUND if not found
     */
    @GetMapping("/advertisements/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisementById(@PathVariable String id) {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(id);

        // link to self
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class).getAdvertisementById(id)).withSelfRel());

        // link to advertisments list
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .getAdvertisements(Optional.of(0), Optional.of(10))).withRel("advertisements"));
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .getActiveAdvertisements(Optional.of(0), Optional.of(10))).withRel("active-advertisements"));
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .getClosedAdvertisements(Optional.of(0), Optional.of(10))).withRel("closed-advertisements"));

        return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
    }

    /**
     * Retrieves all advertisements with pagination.
     *
     * @param page the page number
     * @param size the size of the page
     * @return a page of advertisement data transfer objects with HTTP status OK
     */
    @GetMapping("/advertisements")
    public ResponseEntity<CollectionModel<AdvertisementDTO>> getAdvertisements(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<AdvertisementDTO> advertisementsDTO = advertisementService.getAllAdvertisements(_page, _size);

        advertisementsDTO.forEach(advertisement ->
                advertisement.add(linkTo(methodOn(AdvertisementController.class)
                        .getAdvertisementById(advertisement.getId())).withSelfRel()));

        Link selfLink = linkTo(methodOn(AdvertisementController.class)
                .getAdvertisements(Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (advertisementsDTO.hasNext()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisements(Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (advertisementsDTO.hasPrevious()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisements(Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(advertisementsDTO.getContent(), links), HttpStatus.OK);
    }


    /**
     * Retrieves a paginated list of active advertisements.
     *
     * @param page Optional parameter for the page number (default is 0).
     * @param size Optional parameter for the page size (default is 10).
     * @return A ResponseEntity containing a CollectionModel of AdvertisementDTO with HATEOAS links,
     * including self, next, and previous page links and HTTP status 200 (Ok) if retrieval is successful.
     */
    @GetMapping("/advertisements/active")
    public ResponseEntity<CollectionModel<AdvertisementDTO>> getActiveAdvertisements(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<AdvertisementDTO> advertisementsDTO = advertisementService.getActiveAdvertisements(_page, _size);

        advertisementsDTO.forEach(advertisement ->
                advertisement.add(linkTo(methodOn(AdvertisementController.class)
                        .getAdvertisementById(advertisement.getId())).withSelfRel()));

        Link selfLink = linkTo(methodOn(AdvertisementController.class)
                .getAdvertisements(Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (advertisementsDTO.hasNext()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisements(Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (advertisementsDTO.hasPrevious()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisements(Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(advertisementsDTO.getContent(), links), HttpStatus.OK);
    }


    /**
     * Retrieves a paginated list of closed advertisements.
     *
     * @param page Optional parameter for the page number (default is 0).
     * @param size Optional parameter for the page size (default is 10).
     * @return A ResponseEntity containing a CollectionModel of AdvertisementDTO with HATEOAS links,
     * including self, next, and previous page links and HTTP status 200 (Ok) if retrieval is successful.
     */
    @GetMapping("/advertisements/closed")
    public ResponseEntity<CollectionModel<AdvertisementDTO>> getClosedAdvertisements(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<AdvertisementDTO> advertisementsDTO = advertisementService.getClosedAdvertisements(_page, _size);

        advertisementsDTO.forEach(advertisement ->
                advertisement.add(linkTo(methodOn(AdvertisementController.class)
                        .getAdvertisementById(advertisement.getId())).withSelfRel()));

        Link selfLink = linkTo(methodOn(AdvertisementController.class)
                .getAdvertisements(Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (advertisementsDTO.hasNext()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisements(Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (advertisementsDTO.hasPrevious()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisements(Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(advertisementsDTO.getContent(), links), HttpStatus.OK);
    }

    /**
     * Retrieves a paginated list of advertisements by client ID.
     *
     * @param clientId the ID of the client
     * @param page Optional parameter for the page number (default is 0)
     * @param size Optional parameter for the page size (default is 10)
     * @return a ResponseEntity containing a CollectionModel of AdvertisementDTO with HATEOAS links,
     *         including self, next, and previous page links and HTTP status OK if retrieval is successful
     */
    @GetMapping("/advertisements/{clientId}")
    public ResponseEntity<CollectionModel<AdvertisementDTO>> getAdvertisementsByClientId(
            @PathVariable String clientId,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<AdvertisementDTO> advertisementsDTO = advertisementService.getAdvertisementsByClientId(_page, _size, clientId);

        Link selfLink = linkTo(methodOn(AdvertisementController.class)
                .getAdvertisementsByClientId(clientId, Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (advertisementsDTO.hasNext()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisementsByClientId(clientId, Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (advertisementsDTO.hasPrevious()) {
            links.add(linkTo(methodOn(AdvertisementController.class)
                    .getAdvertisementsByClientId(clientId, Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(advertisementsDTO.getContent(), links), HttpStatus.OK);
    }


    /**
     * Creates a new advertisement.
     *
     * @param request the advertisement data transfer object
     * @return the created advertisement data transfer object with HTTP status CREATED
     */
    @PostMapping("/advertisements")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestBody AdvertisementDTO request) {
        AdvertisementDTO advertisementDTO = advertisementService.createAdvertisement(request);

        // self
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .createAdvertisement(request)).withSelfRel());

        return new ResponseEntity<>(advertisementDTO, HttpStatus.CREATED);
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
    public ResponseEntity<AdvertisementDTO> updateAdvertisement(@PathVariable("id") String id, @RequestBody AdvertisementUpdateDTO request) {
        AdvertisementDTO advertisementDTO = advertisementService.updateAdvertisement(id, request);

        // self
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .updateAdvertisement(id, request)).withSelfRel());

        return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
    }


    /**
     * Deletes an advertisement by its ID.
     * If the advertisement exists and meets the deletion criteria, it is removed from the system.
     *
     * @param id The ID of the advertisement to be deleted.
     * @return A ResponseEntity with HTTP status NO CONTENT if the deletion is successful.
     */
    @DeleteMapping("/advertisements/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable String id) {
        advertisementService.deleteAdvertisement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Create a new advertisement request.
     *
     * @param id The unique identifier of the advertisement.
     * @param requestDTO The request data for creating the advertisement request.
     * @return A {@code ResponseEntity<RequestResponseDTO>} containing the response data.
     */
    @PostMapping("/advertisements/{id}/requests")
    public ResponseEntity<RequestResponseDTO> createAdvertisementRequest(@PathVariable String id, @RequestBody RequestDTO requestDTO) {
        RequestResponseDTO response = advertisementService.createAdvertisementRequest(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieve an advertisement request by its advertisement ID and request ID.
     *
     * @param adId The unique identifier of the advertisement.
     * @param requestId The unique identifier of the request associated with the advertisement.
     * @return A {@link ResponseEntity} containing the {@link RequestResponseDTO} with the advertisement request details,
     *         and an HTTP status of 200 OK if the request is successful.
     */
    @GetMapping("/advertisements/{adId}/requests/{requestId}")
    public ResponseEntity<RequestResponseDTO> getAdvertisementRequestById(@PathVariable String adId, @PathVariable String requestId) {
        RequestResponseDTO response = advertisementService.getAdvertisementRequestById(adId, requestId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of a request for a specific advertisement.
     * This method handles a PATCH request and invokes the service to update the request's status for the specified advertisement.
     *
     * @param adId The unique identifier of the advertisement.
     * @param requestId The unique identifier of the request.
     * @param requestDTO The object containing the new status information for the request.
     *
     * @return A {@link ResponseEntity} containing a {@link RequestResponseDTO} object with the details of the operation's response, including HTTP status.
     */
    @PatchMapping("/advertisements/{adId}/requests/{requestId}/status")
    public ResponseEntity<RequestResponseDTO> patchAdvertisementRequestStatus(@PathVariable String adId, @PathVariable String requestId, @RequestBody RequestStatusDTO requestDTO) {
        RequestResponseDTO response = advertisementService.patchAdvertisementRequestStatus(adId, requestId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
