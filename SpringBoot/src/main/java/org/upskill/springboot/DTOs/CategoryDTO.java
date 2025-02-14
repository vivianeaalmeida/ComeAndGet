package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) for Category.
 * This class is used to transfer category data between layers.
 * It contains the necessary fields to represent a Category in a simplified form.
 * It also extends {@link RepresentationModel} to support HATEOAS.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO extends RepresentationModel<CategoryDTO> {
    /**
     * The unique identifier for the category.
     */
    private String id;

    /**
     * The designation or name of the category.
     */
    private String designation;
}