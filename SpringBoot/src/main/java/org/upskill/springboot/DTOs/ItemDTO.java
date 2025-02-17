package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) for Advertisement.
 * This class is used to transfer advertisement data between layers.
 * It contains the necessary fields to represent an Item in a simplified form.
 * It also extends {@link RepresentationModel} to support HATEOAS.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

    /**
     * The unique identifier for the item.
     */
    private String id;

    /**
     * The name of the item.
     */
    private String image;

    /**
     * The condition of the item (NEW or USED).
     */
    private String condition;

    /**
     * The category associated with the item.
     */
    private CategoryDTO category;
}