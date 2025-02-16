package org.upskill.springboot.Models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a category in the system.
 * This class contains the details of a category, such as its unique ID and designation.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    /**
     * The unique identifier for the category.
     * It is generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The designation or name of the category.
     * This is a required field with a minimum length of 5 characters and a maximum of 50 characters.
     * The designation must be unique across the system.
     */
    @NonNull
    @Column(unique = true, length = 50)
    private String designation;
}