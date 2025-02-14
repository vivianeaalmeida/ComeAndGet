package org.upskill.springboot.Models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an item in the system.
 * This class contains the details of the item, such as its unique ID, image, condition, and associated category.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {

    /**
     * Enumeration representing the possible conditions of an item.
     */
    public enum Condition {
        EXCELLENT,
        GOOD,
        ACCEPTABLE,
        POOR
    }

    /**
     * The unique identifier for the item.
     * It is automatically generated using the UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The image URL or reference for the item.
     * This is a required field.
     */
    @NonNull
    private String image;

    /**
     * The condition of the item.
     * This is a required field and is stored as an ordinal (numeric) value in the database.
     */
    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private Condition condition;

    /**
     * The category to which the item belongs.
     * This is a required field and represents a many-to-one relationship with the Category entity.
     * The item is linked to a category by its unique `category_id`.
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}