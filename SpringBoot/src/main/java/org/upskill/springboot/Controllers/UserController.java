package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Services.UserService;

import java.util.List;

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
//        userDTO.add(linkTo(methodOn(UserController.class).getUsers(Optional.of(1), Optional.of(10))).withRel("Users"));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<List<RequestResponseDTO>> getRequestsByUserId(@PathVariable String userId) {
        List<RequestResponseDTO> requestDTOs = this.userService.getRequestsByUserId(userId);
        return new ResponseEntity<>(requestDTOs, HttpStatus.OK);
    }

    /*
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
     */

}
