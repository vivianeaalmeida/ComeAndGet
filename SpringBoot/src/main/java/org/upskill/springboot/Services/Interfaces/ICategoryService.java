package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.CategoryDTO;

public interface ICategoryService {
    Page<CategoryDTO> getCategories(int page, int size);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(String id, CategoryDTO categoryDTO);
    void deleteCategory(String id);
}
