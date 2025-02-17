package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.upskill.springboot.DTOs.RequestResponseDTO;
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

    /**
     * Finds a request by its ID and associated advertisement ID.
     *
     * @param id the unique identifier of the request
     * @param advertisementId the unique identifier of the advertisement
     * @return an {@link Optional} containing the found request, or empty if no request matches the criteria
     */
    Optional<Request> findByIdAndAdvertisementId(String id, String advertisementId);

    /**
     * Retrieves a list of requests associated with a specific user ID.
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link Request} entities linked to the given user ID
     */
    List<Request> findRequestByUser_Id(String userId);

}
