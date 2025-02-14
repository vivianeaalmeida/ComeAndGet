package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Models.Item;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setImage(item.getImage());
        itemDTO.setCondition(item.getCondition());
        itemDTO.setCategory(item.getCategory());

        return itemDTO;
    }


    public static Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setImage(itemDTO.getImage());
        item.setCondition(itemDTO.getCondition());
        item.setCategory(itemDTO.getCategory());

        return item;
    }
}
