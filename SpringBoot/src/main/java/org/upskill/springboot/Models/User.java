package org.upskill.springboot.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a user in the system.
 * This class contains details of the user, such as their name, email, phone number and role.
 */
@Entity
@Table(name = "user_temp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    /**
     * Enumeration representing the possible roles of a user.
     * The user can either be an ADMIN or a USER.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    /**
     * The name of the user.
     * This is a required field with a minimum length of 5 characters and a maximum of 100 characters.
     */
    @NonNull
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters.")
    public String name;


    /**
     * The email of the user.
     * This is a required field with a minimum length of 5 characters and a maximum of 60 characters.
     */
    @Size(min = 5, max = 60, message = "Email must be between 5 and 60 characters.")
    @NonNull
    public String email;

    /**
     * The phone number of the user.
     * This is a required field.
     */
    @NonNull
    public String phoneNumber;

    /**
     * The role of the user.
     * This is a required field.
     */
    @NonNull
    public String role;
}
