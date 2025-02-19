package org.upskill.springboot.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
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

        Page<AdvertisementDTO> advertisementsDTO = advertisementService.getAdvertisements(_page, _size);

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
    @GetMapping("users/{clientId}/advertisements")
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
    @PostMapping(value = "/advertisements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdvertisementDTO> createAdvertisement(
            @RequestPart(value = "advertisementDTO", required = true) @Valid AdvertisementDTO request,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        System.out.println("Esta entrando no metodo");
        // Chama o Service para criar o anúncio e salvar a imagem
        AdvertisementDTO advertisementDTO = null;
        try {
            advertisementDTO = advertisementService.createAdvertisement(request, imageFile);

            // Adiciona o link HATEOAS para o próprio recurso
            advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                    .createAdvertisement(request, imageFile)).withSelfRel());

            return ResponseEntity.status(HttpStatus.CREATED).body(advertisementDTO);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing image", e);
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

        // self
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .updateAdvertisement(id, request)).withSelfRel());

        return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
    }

    /**
     * Deactivates an advertisement by updating the status of an advertisement to INACTIVE.
     *
     * @param id The ID of the advertisement to be deactivated.
     * @return The deactivated advertisement DTO
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
     * @return A {@code ResponseEntity<RequestResponseDTO>} containing the response data.
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
     * @return A {@link ResponseEntity} containing the {@link ReservationAttemptResponseDTO} with the advertisement request details,
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
     * @return A {@link ResponseEntity} containing a {@link ReservationAttemptResponseDTO} object with the details of the operation's response, including the HTTP status.
     */
    @PatchMapping("/advertisements/{adId}/reservationAttempts/{reservationId}/status")
    public ResponseEntity<ReservationAttemptResponseDTO> patchReservationAttemptStatus(@PathVariable String adId, @PathVariable String reservationId, @RequestBody ReservationAttemptStatusDTO requestDTO) {
        ReservationAttemptResponseDTO response = advertisementService.patchAdvertisementRequestStatus(adId, reservationId , requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Endpoint to retrieve active advertisements based on search filters and pagination.
     * This method allows the user to search for active advertisements by applying filters such as keyword, municipality, and category,
     * while also providing pagination for the results.
     *
     * @param page The page number (optional). If not provided, the default value will be 0.
     * @param size The number of advertisements per page (optional). If not provided, the default value will be 10.
     * @param keyword A keyword to search advertisements whose title or description contain the specified text (optional).
     * @param municipality The municipality to filter the advertisements (optional).
     * @param category The category to filter the advertisements (optional).
     *
     * @return A {@link ResponseEntity} containing a {@link CollectionModel} with a list of {@link AdvertisementDTO}s
     *         that match the provided criteria, along with pagination navigation links (if applicable).
     *         The HTTP status returned will be 200 OK if the operation is successful.
     *
     */
    @GetMapping("/advertisements/active/search")
    public ResponseEntity<CollectionModel<AdvertisementDTO>> searchAdvertisements(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> keyword,
            @RequestParam Optional<String> municipality,
            @RequestParam Optional<String> category
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<AdvertisementDTO> advertisementsDTO = advertisementService.searchAdvertisements(
                _page,
                _size,
                municipality.orElse(null),
                keyword.orElse(null),
                category.orElse(null)
        );

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
}
