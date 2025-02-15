package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) for User.
 * This class is used to transfer user data between layers.
 * It contains the necessary fields to represent a User in a simplified form.
 * It also extends {@link RepresentationModel} to support HATEOAS.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {
    /**
     * The unique identifier for the user.
     */
    public String id;

    /**
     * The name of the user.
     */
    public String name;

    /**
     * The email of the user.
     */
    public String email;

    /**
     * The phone number of the user.
     */
    public String phoneNumber;

    /**
     * The role of the user.
     */
    public String role;
}
