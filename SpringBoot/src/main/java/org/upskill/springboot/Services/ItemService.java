package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Exceptions.CategoryValidationException;
import org.upskill.springboot.Exceptions.ItemValidationException;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.CategoryRepository;
import org.upskill.springboot.Repositories.ItemRepository;
import org.upskill.springboot.Services.Interfaces.IItemService;

@Service
public class ItemService implements IItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ItemDTO createItem(ItemDTO itemDTO) {
        validateItem(itemDTO);

        Item item = ItemMapper.toEntity(itemDTO);
        item = this.itemRepository.save(item);

        return ItemMapper.toDTO(item);
    }

    @Override
    public ItemDTO getItemById(long id) {
        return null;
    }

    @Override
    public ItemDTO updateItem(long id, ItemDTO itemDTO) {
        return null;
    }

    @Override
    public ItemDTO deleteItem(long id) {
        return null;
    }

    public void validateItem(ItemDTO itemDTO) {
        // Validação da imagem
        if (itemDTO.getImage() == null || itemDTO.getImage().isEmpty()) {
            throw new ItemValidationException("The image URL must be provided.");
        }

        // Validação da condição
        if (itemDTO.getCondition() == null) {
            throw new ItemValidationException("The condition must be provided.");
        }

        // Validação da categoria
        if (itemDTO.getCategory().getId() == null) {
            throw new ItemValidationException("The category ID must be provided.");
        }
        Category category = categoryRepository.findById(itemDTO.getCategory().getId())
                .orElseThrow(() -> new CategoryValidationException("Category not found with ID: " + itemDTO.getCategory().getId()));
    }
}
