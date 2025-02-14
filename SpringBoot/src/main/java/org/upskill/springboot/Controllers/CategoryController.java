package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.Services.CategoryService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO request) {
        CategoryDTO categoryDTO = categoryService.createCategory(request);

        // self
        categoryDTO.add(linkTo(methodOn(CategoryController.class)
                .createCategory(request)).withSelfRel());

        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }
}
