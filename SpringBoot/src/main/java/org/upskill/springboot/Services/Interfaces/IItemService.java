package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.ItemDTO;

/**
 * Service interface for managing items.
 */
public interface IItemService {
    /**
     * Retrieves an item by its ID.
     *
     * @param id the ID of the item
     * @return the item data transfer object corresponding to the given ID
     */
    ItemDTO getItemById(String id);

    /**
     * Creates a new item.
     *
     * @param itemDTO the item data transfer object containing the details of the item
     * @return the created item data transfer object
     */
    ItemDTO createItem(ItemDTO itemDTO);


    /**
     * Updates an existing item.
     *
     * @param id                 the ID of the item to be updated
     * @param itemDto            the item data transfer object containing the updated details
     * @return the updated item data transfer object
     */
    ItemDTO updateItem(String id, ItemDTO itemDto);
}