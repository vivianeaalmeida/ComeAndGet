package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.Services.CategoryService;

import java.util.List;

/**
 * REST Controller for categories operations (CRUD) that supports pagination and HATEOAS links for navigation.
 */
@RestController
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Retrieves a paginated list of categories.
     *
     * @return A ResponseEntity containing a list of CategoryDTO and HTTP status 200 (Ok)
     * if get is successful.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoriesDTO = categoryService.getCategories();
        return new ResponseEntity<>(categoriesDTO, HttpStatus.OK);
    }

    /**
     * Creates a new category.
     *
     * @param request The CategoryDTO containing the new category data.
     * @return A ResponseEntity containing the created CategoryDTO with a self HATEOAS link
     * and HTTP status 201 (Created) if creation is successful.
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO request) {
        CategoryDTO categoryDTO = categoryService.createCategory(request);

        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

    /**
     * Updates a category by its ID.
     * If the category exists and meets the update criteria, it is updated in the system.
     *
     * @param id The ID of the category to be updated.
     * @param request The CategoryDTO containing the updated category data.
     * @return A ResponseEntity containing the updated CategoryDTO with a self HATEOAS link
     * and HTTP status 200 (Ok) if the update is successful.
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") String id, @RequestBody CategoryDTO request) {
        if (!id.equals(request.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        CategoryDTO categoryDTO = categoryService.updateCategory(id, request);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    /**
     * Deletes a category by its ID.
     * If the category exists and meets the deletion criteria, it is removed from the system.
     *
     * @param id The ID of the category to be deleted.
     * @return A ResponseEntity with HTTP status 204 (No Content) if the deletion is successful.
     */
    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
