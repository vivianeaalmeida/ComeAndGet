package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.Services.AdvertisementService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping("/advertisements")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestBody AdvertisementDTO request) {
        AdvertisementDTO advertisementDTO = advertisementService.createAdvertisement(request);

        // self
        advertisementDTO.add(linkTo(methodOn(AdvertisementController.class)
                .createAdvertisement(request)).withSelfRel());

        return new ResponseEntity<>(advertisementDTO, HttpStatus.CREATED);
    }
}
