package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Models.Item;

public interface IItemService {
    ItemDTO createItem(ItemDTO itemDTO);

    ItemDTO getItemById(String id);

    ItemDTO updateItem (String id, ItemDTO itemDto);

    void deleteItem (String id);
}