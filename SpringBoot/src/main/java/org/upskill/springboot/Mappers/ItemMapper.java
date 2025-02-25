package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Models.Item;

/**
 * Mapper class to convert between {@link Item} entity and {@link ItemDTO} data transfer object.
 * This class contains methods for transforming data between the entity and DTO representations.
 */
public class ItemMapper {

    /**
     * Converts an Item entity to a ItemDTO.
     * @param item The Item entity to be converted.
     * @return A ItemDTO containing the data from the provided Item entity.
     */
    public static ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setImage(item.getImage());
        itemDTO.setCondition(item.getCondition().toString());
        itemDTO.setCategory(CategoryMapper.toDTO(item.getCategory()));

        return itemDTO;
    }

    /**
     * Converts a ItemDTO to an Item entity.
     *
     * @param itemDTO The item DTO to be converted.
     * @return A category containing the data from the provided {ItemDTO.
     */
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