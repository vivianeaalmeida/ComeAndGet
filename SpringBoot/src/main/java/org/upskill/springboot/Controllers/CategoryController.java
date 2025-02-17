package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.Services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
     * @param page Optional parameter for the page number (default is 0).
     * @param size Optional parameter for the page size (default is 10).
     * @return A ResponseEntity containing a CollectionModel of CategoryDTO with HATEOAS links,
     * including self, next, and previous page links and HTTP status 200 (Ok) if get is successful.
     */
    @GetMapping("/categories")
    public ResponseEntity<CollectionModel<CategoryDTO>> getCategories(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = size.orElse(10);

        Page<CategoryDTO> categoriesDTO = categoryService.getCategories(_page, _size);

        Link selfLink = linkTo(methodOn(CategoryController.class)
                .getCategories(Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (categoriesDTO.hasNext()) {
            links.add(linkTo(methodOn(CategoryController.class)
                    .getCategories(Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (categoriesDTO.hasPrevious()) {
            links.add(linkTo(methodOn(CategoryController.class)
                    .getCategories(Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(categoriesDTO.getContent(), links), HttpStatus.OK);
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

        // self
        categoryDTO.add(linkTo(methodOn(CategoryController.class)
                .createCategory(request)).withSelfRel());

        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

    /**
     * Deletes a category by its ID.
     * If the category exists and meets the deletion criteria, it is removed from the system.
     *
     * @param id The ID of the category to be deleted.
     * @return A ResponseEntity with HTTP status 204 (No Content) if the deletion is successful.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
        CategoryDTO categoryDTO = categoryService.updateCategory(id, request);

        // self
        categoryDTO.add(linkTo(methodOn(CategoryController.class)
                .updateCategory(id, request)).withSelfRel());

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}
