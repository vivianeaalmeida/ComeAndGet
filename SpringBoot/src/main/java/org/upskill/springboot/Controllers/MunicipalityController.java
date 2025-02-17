package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.MunicipalityDTO;
import org.upskill.springboot.WebClient.MunicipalityWebClient;
import org.upskill.springboot.WebClient.WebClientConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MunicipalityController extends BaseController {

    @Autowired
    private MunicipalityWebClient municipalityWebClient;


    @GetMapping("/municipalities")
    public ResponseEntity<List<String>> getAdvertisements() {
        List<String> municipalities = municipalityWebClient.getMunicipalities();

        return new ResponseEntity<>(municipalities, HttpStatus.OK);
    }
}
