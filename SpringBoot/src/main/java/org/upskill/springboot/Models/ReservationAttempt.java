package org.upskill.springboot.Models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

public class ReservationAttempt {

    /**
     * Enumeration representing the possible statuses of a reservation attempt.
     */
    public enum ReservationAttemptStatus {
        PENDING,
        CANCELED,
        REJECTED,
        ACCEPTED,
        DONATED
    }

    /**
     * The unique identifier for the reservation attempt.
     * It is automatically generated using the UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The advertisement associated with this reservation attempt.
     * This is a required field and represents a many-to-one relationship with the Advertisement entity.
     * The reservation attempt is linked to an advertisement by its unique `advertisement_id`.
     */
    @ManyToOne
    @JoinColumn(name = "advertisement_id", nullable = false)
    private Advertisement advertisement;

    /**
     * The ID of the user who made the reservation attempt.
     * This is a required field.
     */
    @Nullable
    @Column(name = "client_id")
    private String clientId;

    /**
     * The date the reservation attempt was created.
     * This is a required field.
     */
    @NonNull
    private LocalDate date;

    /**
     * The status of the reservation attempt.
     * This is a required field and represents the current state of the reservation attempt.
     * The status is stored in the database as a numeric value.
     */
    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private ReservationAttempt.ReservationAttemptStatus status;

    /**
     * Default constructor for the Reservation Attempt class.
     * Sets the date to the current date and the status to PENDING by default.
     */
    public ReservationAttempt() {
        this.date = LocalDate.now();
        this.status = ReservationAttemptStatus.PENDING;
    }
}