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

    public enum Condition {
        EXCELLENT,
        GOOD,
        ACCEPTABLE,
        POOR
    }

    private String id;

    private String image;

    private Item.Condition condition;

    private Category category;
}
