package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upskill.springboot.Models.Item;

/**
 * Repository interface for performing CRUD operations on {@link Item} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
public interface ItemRepository extends JpaRepository<Item, String> {

    /**
     * Checks if there are any items associated with a specific category.
     *
     * @param categoryId The unique identifier of the category.
     * @return {@code true} if at least one item exists for the given category, otherwise {@code false}.
     */
    boolean existsByCategory_Id(String categoryId);
}