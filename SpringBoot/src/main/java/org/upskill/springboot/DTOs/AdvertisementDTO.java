package org.upskill.springboot.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Advertisement.
 * This class is used to transfer advertisement data between layers.
 * It contains the necessary fields to represent an Advertisement in a simplified form.
 * It also extends {@link RepresentationModel} to support HATEOAS.
 */
@Getter
@Setter
public class AdvertisementDTO extends RepresentationModel<AdvertisementDTO> {

    /**
     * The unique identifier for the advertisement.
     */
    private String id;

    /**
     * The title of the advertisement.
     */
    private String title;

    /**
     * A detailed description of the advertisement.
     */
    private String description;

    /**
     * The initial date when the advertisement was created.
     */
    private LocalDate initialDate;

    /**
     * The current status of the advertisement (ACTIVE or CLOSED).
     */
    private String status;

    /**
     * The item associated with the advertisement.
     */
    private ItemDTO item;

    /**
     * The unique client ID associated with the advertisement.
     */
    private String clientId;
}
