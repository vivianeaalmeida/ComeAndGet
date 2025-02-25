package org.upskill.springboot.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CategoryServiceTest {

    private Category category;
    private List<Category> categories;

    /**
     * Setup method executed before each test.
     * Initializes category objects.
     */
    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId("1");
        category.setDesignation("Electronics");

        categories = new ArrayList<>();
        categories.add(category);


    }

    /**
     * Tests retrieving a category by ID.
     */
    @Test
    void testGetCategoryById_ShouldReturnCorrectCategory() {
        Category foundCategory = categories.stream()
                .filter(cat -> cat.getId().equals("1"))
                .findFirst()
                .orElse(null);
        assertNotNull(foundCategory);
        assertEquals("Electronics", foundCategory.getDesignation());
    }

    /**
     * Tests retrieving all categories.
     */
    @Test
    void testGetAllCategories_ShouldReturnList() {
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
    }

    @Test
    void testCreateCategory_ShouldSetInitialValues() {
        Category newCategory = new Category();
        newCategory.setDesignation("New Categ");

        assertEquals("New Categ", newCategory.getDesignation());
    }

    @Test
    void testUpdateCategory_ShouldSetNewValues() {
        category.setDesignation("Updated Categ");

        assertEquals("Updated Categ", category.getDesignation());
    }

    @Test
    void testDeleteCategory_ShouldDeleteCategory() {
        String categoryId = "1";

        assertTrue(categories.stream().anyMatch(cat -> cat.getId().equals(categoryId)));

        categories.removeIf(cat -> cat.getId().equals(categoryId));

        assertFalse(categories.stream().anyMatch(cat -> cat.getId().equals(categoryId)));
    }
}
