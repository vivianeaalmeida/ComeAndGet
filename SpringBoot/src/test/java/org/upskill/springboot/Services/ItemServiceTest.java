package org.upskill.springboot.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Exceptions.InvalidFileExtensionException;
import org.upskill.springboot.Exceptions.ItemNotFoundException;
import org.upskill.springboot.Exceptions.ItemValidationException;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Models.Item;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ItemService.
 * This class contains unit tests for various methods in the ItemService.
 */
class ItemServiceTest {

    private ItemService itemService;
    private Item item;
    private Category category;
    private CategoryDTO categoryDTO;

    /**
     * Setup method executed before each test.
     * Initializes item and category objects.
     */
    @BeforeEach
    void setUp() {
        itemService = new ItemService();

        category = new Category();
        category.setId("cat123");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId("cat123");

        item = new Item();
        item.setId("item123");
        item.setImage("image.jpg");
        item.setCondition(Item.Condition.GOOD);
        item.setCategory(category);
    }


    /**
     * Tests creating an item with valid data.
     */
    @Test
    void testCreateItem_ShouldSetInitialValues() {
        Item newItem = new Item();
        newItem.setImage("item.jpg");
        newItem.setCondition(Item.Condition.EXCELLENT);
        newItem.setCategory(category);

        assertEquals("item.jpg", newItem.getImage());
        assertEquals(Item.Condition.EXCELLENT, newItem.getCondition());
        assertNotNull(newItem.getCategory());
    }

    /**
     * Tests validation of an invalid item.
     */
    @Test
    void testValidateItem_ShouldThrowException_WhenConditionIsNull() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCategory(categoryDTO);
        assertThrows(ItemValidationException.class, () -> itemService.validateItem(itemDTO));
    }


    /**
     * Tests uploading an image with a valid file.
     */
    @Test
    void testUploadItemImage_ShouldThrowException_WhenFileIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> itemService.uploadItemImage(null));
    }
}

