package org.upskill.springboot.Models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Represents a request in the system.
 * This class contains the details of a request, such as its unique ID, associated advertisement,
 * client ID, date, and request status.
 */
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Request {

    /**
     * Enumeration representing the possible statuses of a request.
     */
    public enum RequestStatus {
        PENDING,
        CANCELED,
        REJECTED,
        ACCEPTED,
        DONATED
    }

    /**
     * The unique identifier for the request.
     * It is automatically generated using the UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The advertisement associated with this request.
     * This is a required field and represents a many-to-one relationship with the Advertisement entity.
     * The request is linked to an advertisement by its unique `advertisement_id`.
     */
    @ManyToOne
    @JoinColumn(name = "advertisement_id", nullable = false)
    private Advertisement advertisement;

    /**
     * The ID of the user who made the request.
     * This is a required field.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The date the request was created.
     * This is a required field.
     */
    @NonNull
    private LocalDate date;

    /**
     * The status of the request.
     * This is a required field and represents the current state of the request.
     * The status is stored in the database as a numeric value.
     */
    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    /**
     * Default constructor for the Request class.
     * Sets the date to the current date and the status to PENDING by default.
     */
    public Request() {
        this.date = LocalDate.now();
        this.status = RequestStatus.PENDING;
    }
}