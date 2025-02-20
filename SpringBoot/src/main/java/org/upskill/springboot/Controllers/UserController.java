package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Services.UserService;

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
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}
