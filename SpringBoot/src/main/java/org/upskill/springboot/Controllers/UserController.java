package org.upskill.springboot.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing users.
 */
@RestController
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO request) {
        UserDTO userDTO = userService.createUser(request);

        // self
        userDTO.add(linkTo(methodOn(UserController.class)
                .createUser(request)).withSelfRel());

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") String id) {
        UserDTO userDTO = this.userService.getUserById(id);

        userDTO.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/ReservationAttempt")
    public ResponseEntity<CollectionModel<ReservationAttemptResponseDTO>> getRequestsByUserId(@PathVariable String userId,
                                                                                              @RequestParam Optional<Integer> page,
                                                                                              @RequestParam Optional<Integer> size) {
        int _page = page.orElse(0);
        int _size = size.orElse(10);
        Page<ReservationAttemptResponseDTO> response = this.userService.getReservationAttemptByUserId(userId,_page, _size);
        Link selfLink = linkTo(methodOn(UserController.class)
                .getAdvertisementsRequestsByUserId(userId, Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (response.hasNext()) {
            links.add(linkTo(methodOn(UserController.class)
                    .getAdvertisementsRequestsByUserId(userId, Optional.of(_page+1), Optional.of(_size))).withRel("next"));
        }
        if (response.hasPrevious()) {
            links.add(linkTo(methodOn(UserController.class)
                    .getAdvertisementsRequestsByUserId(userId, Optional.of(_page+1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(response.getContent(), links), HttpStatus.OK);
    }

   @GetMapping("/users/{userId}/advertisements/reservationAttempt")
    public ResponseEntity<CollectionModel<ReservationAttemptResponseDTO>>getAdvertisementsRequestsByUserId(
            @PathVariable String userId,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        int _page = page.orElse(0);
        int _size = size.orElse(10);
        Page<ReservationAttemptResponseDTO> response = userService.getReservationAttemptFromAdvertisementOfUser(userId, _page, _size);


       Link selfLink = linkTo(methodOn(UserController.class)
               .getAdvertisementsRequestsByUserId(userId, Optional.of(_page), Optional.of(_size))).withSelfRel();

       List<Link> links = new ArrayList<>();
       links.add(selfLink);

       if (response.hasNext()) {
           links.add(linkTo(methodOn(UserController.class)
                   .getAdvertisementsRequestsByUserId(userId, Optional.of(_page+1), Optional.of(_size))).withRel("next"));
       }
       if (response.hasPrevious()) {
           links.add(linkTo(methodOn(UserController.class)
                   .getAdvertisementsRequestsByUserId(userId, Optional.of(_page+1), Optional.of(_size))).withRel("previous"));
       }

       return new ResponseEntity<>(CollectionModel.of(response.getContent(), links), HttpStatus.OK);
    }
}
