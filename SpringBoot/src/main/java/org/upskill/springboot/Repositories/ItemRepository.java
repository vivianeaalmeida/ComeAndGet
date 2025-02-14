package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upskill.springboot.Models.Item;

public interface ItemRepository extends JpaRepository<Item, String> {

    /**
     * Counts the number of items associated with a specific category.
     *
     * @param categoryId The unique identifier of the category for which the count of items is required.
     * @return The number of items that belong to the specified category.
     */
    int countByCategory_Id(String categoryId);
}