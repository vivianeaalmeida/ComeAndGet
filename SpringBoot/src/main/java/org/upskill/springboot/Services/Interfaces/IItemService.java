package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.ItemDTO;

public interface IItemService {
    ItemDTO createItem(ItemDTO itemDTO);

    ItemDTO getItemById(long id);

    ItemDTO updateItem (long id, ItemDTO itemDto);

    ItemDTO deleteItem (long id);
}
