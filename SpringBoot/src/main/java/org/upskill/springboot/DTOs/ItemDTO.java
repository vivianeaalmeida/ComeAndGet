package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Models.Item;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private String id;

    private String image;

    private String condition;

    private CategoryDTO category;
}