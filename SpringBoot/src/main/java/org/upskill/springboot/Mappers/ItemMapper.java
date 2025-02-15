package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Models.Item;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setImage(item.getImage());
        itemDTO.setCondition(item.getCondition().toString());
        itemDTO.setCategory(CategoryMapper.toDTO(item.getCategory()));

        return itemDTO;
    }

    public static Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setImage(itemDTO.getImage());
        // Converts String to Enum
        if (itemDTO.getCondition() != null) {
            item.setCondition(Item.Condition.valueOf(itemDTO.getCondition().toUpperCase()));
        }
        item.setCategory(CategoryMapper.toEntity(itemDTO.getCategory()));

        return item;
    }
}