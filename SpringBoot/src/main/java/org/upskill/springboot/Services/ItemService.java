package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.Repositories.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public boolean hasItemsInCategory(String categoryId) {
        return itemRepository.countByCategory_Id(categoryId) > 0;
    }
}
