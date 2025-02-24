package org.upskill.springboot.DTOs;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.upskill.springboot.Models.Advertisement;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Advertisement.
 * This class is used to transfer advertisement data between layers.
 * It contains the necessary fields to represent an Advertisement in a simplified form.
 */
@Getter
@Setter
public class AdvertisementDTO {

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
    private LocalDate date;

    /**
     * The municipality of the advertisement
     */
    private String municipality;

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
    @Nullable
    private String clientId;

    /**
     * Default constructor that initializes the advertisement with the current date
     * and sets the status to ACTIVE.
     */
    public AdvertisementDTO() {
        this.status = Advertisement.AdvertisementStatus.ACTIVE.toString();
        this.date = LocalDate.now();
    }
}

