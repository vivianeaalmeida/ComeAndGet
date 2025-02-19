package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.Models.Category;

import java.util.List;

/**
 * Service interface for managing categories.
 */
public interface ICategoryService {
    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category
     * @return the category with the given ID
     */
    Category getCategoryById(String id);

    /**
     * Retrieves a list of all categories.
     *
     * @return a page of categories
     */
    List<CategoryDTO> getCategories();

    /**
     * Creates a new category.
     *
     * @param categoryDTO the category data transfer object containing the category details
     * @return the created category DTO
     */
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    /**
     * Updates an existing category.
     *
     * @param id          the ID of the category to be updated
     * @param categoryDTO the category data transfer object containing the updated details
     * @return the updated category DTO
     */
    CategoryDTO updateCategory(String id, CategoryDTO categoryDTO);

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to be deleted
     */
    void deleteCategory(String id);
}
