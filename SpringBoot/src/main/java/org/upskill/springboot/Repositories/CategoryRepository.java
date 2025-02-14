package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upskill.springboot.Models.Category;

/**
 * Repository interface for performing CRUD operations on {@link Category} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * Checks if a category with the given designation already exists in the database.
     * This method is used to ensure that category names are unique.
     *
     * @param designation The designation (name) of the category to check.
     * @return {@code true} if a category with the specified designation exists, false otherwise.
     */
    boolean existsByDesignation(String designation);
}
