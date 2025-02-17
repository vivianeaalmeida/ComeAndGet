package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.upskill.springboot.Models.Request;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Request} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

    /**
     * Finds all requests for a given advertisement.
     *
     * @param advertisementId the ID of the advertisement to query
     * @return a list of requests for the given advertisement
     */
    @Query("SELECT r FROM Request r WHERE r.advertisement.id = :advertisementId")
    List<Request> getRequestsByAdvertisementId(String advertisementId);

    Optional<Request> findByIdAndAdvertisementId(String id, String advertisementId);

    boolean existsByAdvertisement_IdAndUser_Id(String advertisement_id, String user_Id);
}
