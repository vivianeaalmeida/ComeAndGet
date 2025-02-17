package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Exceptions.CategoryNotFoundException;
import org.upskill.springboot.Exceptions.ItemNotFoundException;
import org.upskill.springboot.Exceptions.ItemValidationException;
import org.upskill.springboot.Mappers.CategoryMapper;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.ItemRepository;
import org.upskill.springboot.Services.Interfaces.IItemService;

/**
 * Service class for managing items.
 */
@Service
public class ItemService implements IItemService {
    /**
     * The item repository.
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * The category repository.
     */
    @Autowired
    CategoryService categoryService;

    /**
     * Retrieves ab item by its id. If the item does not exist, an exception is thrown.
     *
     * @param id The unique identifier of the item.
     * @return The ItemDTO corresponding to the id.
     */
    @Override
    public ItemDTO getItemById(String id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toDTO)
                .orElseThrow(ItemNotFoundException::new);
    }

    /**
     * Creates a new item.
     *
     * @param itemDTO the item data transfer object
     * @return the created item data transfer object
     */
    @Override
    public ItemDTO createItem(ItemDTO itemDTO) {
        validateItem(itemDTO);

        Item item = ItemMapper.toEntity(itemDTO);
        item = this.itemRepository.save(item);

        return ItemMapper.toDTO(item);
    }

    @Override
    public ItemDTO updateItem(String id, ItemDTO itemDTO) {
        return null;
    }

    /**
     * Validates the item data transfer object.
     *
     * @param itemDTO the item data transfer object
     * @throws IllegalArgumentException if the item is null
     * @throws ItemValidationException   if the image URL, condition, or category ID is not valid
     */
    public void validateItem(ItemDTO itemDTO) {
        // Item validation
        if (itemDTO == null) {
            throw new IllegalArgumentException("The item must be provided.");
        }

        // Image validation
        if (itemDTO.getImage() == null || itemDTO.getImage().isEmpty()) {
            throw new ItemValidationException("The image URL must be provided.");
        }

        // Condition validation
        if (itemDTO.getCondition() == null) {
            throw new ItemValidationException("The condition must be provided.");
        }

        // Category validation
        if (itemDTO.getCategory().getId() == null) {
            throw new ItemValidationException("The category ID must be provided.");
        }

        // Validate category by calling the CategoryService and throws different exception
        try {
            Category category = categoryService.getCategoryById(itemDTO.getCategory().getId());
            itemDTO.setCategory(CategoryMapper.toDTO(category));
            itemDTO.setCategory(CategoryMapper.toDTO(category));
        } catch (CategoryNotFoundException e) {
            // Catch the exception and throw a more appropriate exception for validation
            throw new ItemValidationException("Category with ID " + itemDTO.getCategory().getId() + " is invalid.");
        }
    }

    /**
     * Checks if there are items associated with a specific category.
     *
     * @param categoryId The identifier of the category.
     * @return {@code true} if there are items associated with the category, {@code false} otherwise.
     */
    public boolean hasItemsInCategory(String categoryId) {
        return itemRepository.countByCategory_Id(categoryId) > 0;
    }
}
