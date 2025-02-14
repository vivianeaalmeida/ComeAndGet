package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.Models.Category;

/**
 * Mapper class to convert between {@link Category} entity and {@link CategoryDTO} data transfer object.
 * This class contains methods for transforming data between the entity and DTO representations.
 */
public class CategoryMapper {

    /**
     * Converts a {@link Category} entity to a {@link CategoryDTO}.
     *
     * @param category The {@link Category} entity to be converted.
     * @return A categoryDTO containing the data from the provided {@link Category} entity.
     */
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setDesignation(category.getDesignation());

        return categoryDTO;
    }

    /**
     * Converts a {@link CategoryDTO} to a {@link Category} entity.
     *
     * @param categoryDTO The category DTO to be converted.
     * @return A category containing the data from the provided {@link CategoryDTO}.
     */
    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setDesignation(categoryDTO.getDesignation());

        return category;
    }
}