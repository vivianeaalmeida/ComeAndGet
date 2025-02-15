package org.upskill.springboot.Models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Represents an advertisement in the system.
 * This class contains details of the advertisement, such as its title, description, initial date,
 * status, associated item and associated client. It also includes a method to close an advertisement
 * if it has been active for more than 30 days.
 */
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Advertisement {

    /**
     * Enumeration representing the possible statuses of an advertisement.
     * The advertisement can either be ACTIVE or CLOSED.
     */
    public enum AdvertisementStatus {
        ACTIVE,
        CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;  // Unique identifier for the advertisement

    /**
     * The title of the advertisement.
     * This is a required field with a minimum length of 5 characters and a maximum of 50 characters.
     */
    @NonNull
    @Min(5)
    @Max(50)
    private String title;

    /**
     * A detailed description of the advertisement.
     * This is a required field with a minimum length of 5 characters and a maximum of 50 characters.
     */
    @NonNull
    @Size(min = 5, max = 50)
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
    @Enumerated(EnumType.ORDINAL)
    private AdvertisementStatus status;

    /**
     * The item associated with the advertisement.
     * This is a required field, and the item is linked by its unique ID.
     */
    @OneToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    /**
     * The unique client ID associated with the advertisement.
     * This is a required field.
     */
    @NonNull
    private String clientId;

    /**
     * Default constructor that initializes the advertisement with the current date
     * and sets the status to ACTIVE.
     */
    public Advertisement() {
        this.initialDate = LocalDate.now();
        this.status = AdvertisementStatus.ACTIVE;
    }

    /**
     * Closes the advertisement if it has expired.
     * The advertisement is considered expired if it has been active for more than 30 days from the initial date.
     * @return true if the advertisement is expired and successfully closed,
     *         false if the advertisement is still within the 30-day period and remains open
     */
    public boolean closeIfExpired() {
        if (LocalDate.now().isAfter(initialDate.plusDays(30))) {
            this.status = AdvertisementStatus.CLOSED;
            return true;
        }
        return false;
    }
}