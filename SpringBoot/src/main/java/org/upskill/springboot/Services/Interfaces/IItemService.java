package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.ItemDTO;

/**
 * Service interface for managing items.
 */
public interface IItemService {

    /**
     * Creates a new item.
     *
     * @param itemDTO the item data transfer object containing the details of the item
     * @return the created item data transfer object
     */
    ItemDTO createItem(ItemDTO itemDTO);
}