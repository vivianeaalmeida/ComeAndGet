package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.Exceptions.NotFoundException;
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
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return the advertisement data transfer object with HTTP status OK, or HTTP status NOT FOUND if not found
     */
    @GetMapping("/advertisements/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisementById(@PathVariable String id) {
        try {
            AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(id);
            return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
}
