package org.upskill.springboot.Models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
    @Min(5)
    @Max(100)
    public String name;


    /**
     * The email of the user.
     * This is a required field with a minimum length of 5 characters and a maximum of 60 characters.
     */
    @Min(5)
    @Max(60)
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
