package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.AdvertisementUpdateDTO;
import org.upskill.springboot.Services.AdvertisementService;

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
    @PostMapping("/advertisements")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(
            @RequestBody AdvertisementDTO request,
            @RequestHeader("Authorization") String authorization) {

        // Chama o service e passa o token JWT
        AdvertisementDTO advertisementDTO = advertisementService.createAdvertisement(request, authorization);

        // Adiciona link HATEOAS
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .createAdvertisement(request, authorization)).withSelfRel());

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
