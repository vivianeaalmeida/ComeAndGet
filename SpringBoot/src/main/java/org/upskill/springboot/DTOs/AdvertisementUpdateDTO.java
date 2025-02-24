package org.upskill.springboot.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Advertisement.
 * This class is used to transfer advertisement data between layers.
 * It contains the necessary fields to represent an Advertisement in a simplified form.
 */
@Getter
@Setter
public class AdvertisementUpdateDTO {

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
     * The status of the advertisement.
     */
    private String status;
}
