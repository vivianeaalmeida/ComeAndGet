package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.Services.Interfaces.IReservationAttemptService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing reservation attempt.
 */
@RestController
public class ReservationAttemptController extends BaseController {
    @Autowired
    private IReservationAttemptService reservationAttemptService;

    /**
     *
     * @param page Optional parameter for the page number (default is 0)
     * @param size Optional parameter for the page size (default is 10)
     * @return a ResponseEntity containing a CollectionModel of ReservationAttemptResponseDTO with HATEOAS links,
     *         including self, next, and previous page links and HTTP status OK if retrieval is successful
     */
    @GetMapping("/reservationAttempts")
    public ResponseEntity<CollectionModel<ReservationAttemptResponseDTO>> getReservationAttempts(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<ReservationAttemptResponseDTO> reservationAttemptsDTO = reservationAttemptService.getReservationAttempts(_page, _size);

        Link selfLink = linkTo(methodOn(ReservationAttemptController.class)
                .getReservationAttempts(Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (reservationAttemptsDTO.hasNext()) {
            links.add(linkTo(methodOn(CategoryController.class)
                    .getCategories(Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (reservationAttemptsDTO.hasPrevious()) {
            links.add(linkTo(methodOn(CategoryController.class)
                    .getCategories(Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(reservationAttemptsDTO.getContent(), links), HttpStatus.OK);
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
        return ResponseEntity.ok(responseDTO);
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
        return ResponseEntity.ok(responseDTO);
    }
}
