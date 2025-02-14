package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.Exceptions.CategoryValidationException;
import org.upskill.springboot.Exceptions.DuplicateCategoryException;
import org.upskill.springboot.Mappers.CategoryMapper;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Repositories.CategoryRepository;
import org.upskill.springboot.Services.Interfaces.ICategoryService;

/**
 * Service class responsible for handling business logic related to categories in the system.
 * This class provides methods for CRUD operations and validating category data.
 */
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    /**
     * Retrieves a paginated list of the existing categories.
     *
     * @param page the page number (zero-based index)
     * @param size the number of elements per page
     * @return a page of a CategoryDTO containing the requested categories
     */
    @Override
    public Page<CategoryDTO> getCategories(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size))
                .map(CategoryMapper::toDTO);
    }

    /**
     * Creates a new category in the system and saves it to the database.
     * After saving, it returns the saved category as a DTO.
     *
     * @param categoryDTO The CategoryDTO object containing the category data to be created.
     * @return A categoryDTO object containing the saved category data.
     */
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Validate the category
        validateCategory(categoryDTO);

        Category category = CategoryMapper.toEntity(categoryDTO);

        category = categoryRepository.save(category);

        return CategoryMapper.toDTO(category);
    }

    /**
     * Validates the data of a category before it is persisted in the system.
     * This method performs the following checks on the category data:
     * <ul>
     *     <li>Verifies if a category with the same designation already exists in the database.</li>
     *     <li>Checks if the category designation is null or empty.</li>
     *     <li>Ensures that the category designation is between 5 and 50 characters</li>
     * </ul>
     *
     * @param categoryDTO The CategoryDTO object containing the category data to be validated.
     * @return {@code true} if the category data is valid.
     * @throws DuplicateCategoryException If a category with the same designation already exists in the database.
     * @throws IllegalArgumentException If the category designation is null, empty, or exceeds the allowed length of 50 characters.
     */
    private boolean validateCategory(CategoryDTO categoryDTO) {
        // Check if the category designation already exists
        if (categoryRepository.existsByDesignation(categoryDTO.getDesignation())) {
            throw new DuplicateCategoryException("Category name '" +
                    categoryDTO.getDesignation() + "' already exists.");
        }

        // Check if the category designation is null or empty
        if (categoryDTO.getDesignation() == null || categoryDTO.getDesignation().isEmpty()) {
            throw new CategoryValidationException("Category name cannot be null or empty");
        }

        // Check if the category has between 5 and 50 characters
        if (categoryDTO.getDesignation().length() < 5 || categoryDTO.getDesignation().length() > 50) {
            throw new CategoryValidationException("Category name must be between 5 and 50 characters");
        }
        return true;
    }
}