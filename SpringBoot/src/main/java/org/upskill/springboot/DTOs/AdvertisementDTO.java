package org.upskill.springboot.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Item;

import java.time.LocalDate;

@Getter
@Setter
public class AdvertisementDTO extends RepresentationModel<AdvertisementDTO> {

    /**
     * Enumeration representing the possible statuses of an advertisement.
     * The advertisement can either be ACTIVE or CLOSED.
     */
    public enum AdvertisementStatus {
        ACTIVE,
        CLOSED
    }

    private String id;  // Unique identifier for the advertisement

    /**
     * The title of the advertisement.
     * This is a required field with a minimum length of 5 characters and a maximum of 50 characters.
     */
    private String title;

    /**
     * A detailed description of the advertisement.
     * This is a required field with a minimum length of 5 characters and a maximum of 50 characters.
     */
    private String description;

    /**
     * The initial date when the advertisement was created.
     * This is a required field.
     */
    private LocalDate initialDate;

    /**
     * The current status of the advertisement (ACTIVE or CLOSED).
     * This is a required field.
     * The status is stored in the database as a numeric value.
     */
    private Advertisement.AdvertisementStatus status;

    /**
     * The item associated with the advertisement.
     * This is a required field, and the item is linked by its unique ID.
     */
    private Item item;

    /**
     * The unique client ID associated with the advertisement.
     * This is a required field.
     */
    private String clientId;
}
