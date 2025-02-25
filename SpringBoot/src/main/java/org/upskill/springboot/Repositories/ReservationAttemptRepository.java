package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upskill.springboot.Models.ReservationAttempt;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link ReservationAttempt} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
@Repository
public interface ReservationAttemptRepository extends JpaRepository<ReservationAttempt, String> {

    /**
     * Finds all reservations attempts of a given advertisement
     * @param advertisementId The unique identifier of the advertisement.
     * @return a list of reservations attempts by advertisement id
     */
    List<ReservationAttempt> findByAdvertisement_Id(String advertisementId);

    /**
     * Checks if there are any requests associated with a specific advertisement.
     *
     * @param advertisementId The unique identifier of the advertisement.
     * @return {@code true} if at least one request exists for the given advertisement, otherwise {@code false}.
     */
    boolean existsByAdvertisement_Id(String advertisementId);

    /**
     * Retrieves a list of ReservationAttempts based on the provided filter criteria.
     * @param reservationAttemptClientId The client ID associated with the reservation attempt (optional).
     * @param advertisementClientId The client ID associated with the advertisement (optional).
     * @param advertisementId advertisementId The ID of the advertisement (optional).
     * @return A list of ReservationAttempt entities that match the provided criteria.
     */
    @Query("SELECT r FROM ReservationAttempt r " +
            "WHERE (:reservationAttemptClientId IS NULL OR r.clientId = :reservationAttemptClientId) " +
            "AND (:advertisementClientId IS NULL OR r.advertisement.clientId = :advertisementClientId) " +
            "AND (:advertisementId IS NULL OR r.advertisement.id = :advertisementId)")
    List<ReservationAttempt> findAllBy(
            @Param("reservationAttemptClientId") String reservationAttemptClientId,
            @Param("advertisementClientId") String advertisementClientId,
            @Param("advertisementId") String advertisementId
    );

    /**
     * Checks if a request exists for a given advertisement and user.
     *
     * @param advertisement_id the ID of the advertisement
     * @param user_Id the ID of the user
     * @return true if a request exists for the given advertisement and user, false otherwise
     */
    boolean existsByAdvertisement_IdAndClientId(String advertisement_id, String user_Id);

    /**
     * Checks if there are any requests in the "DONATED" state for a specific advertisement.
     *
     * @param advertisementId The ID of the advertisement
     * @return {@code true} if there are any "DONATED" requests for the advertisement, {@code false} otherwise.
     */
    @Query("SELECT COUNT(r) > 0 FROM ReservationAttempt r WHERE r.advertisement.id = :advertisementId AND r.status = 4")
    boolean existsDonatedReservationsForAdvertisement(String advertisementId);

    /**
     * Finds all reservation attempts for a specific advertisement and with statuses included in a list.
     *
     * @param advertisementId the ID of the advertisement to query
     * @param statuses a list of statuses to filter the reservation attempts
     * @return a list of ReservationAttempt entities matching the given advertisement ID and statuses
     */
    List<ReservationAttempt> findByAdvertisement_IdAndStatusIn(String advertisementId, List<ReservationAttempt.ReservationAttemptStatus> statuses);
}