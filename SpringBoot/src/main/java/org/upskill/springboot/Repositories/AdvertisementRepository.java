package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.upskill.springboot.Models.Advertisement;

/**
 * Repository interface for performing CRUD operations on {@link Advertisement} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
public interface AdvertisementRepository  extends JpaRepository<Advertisement, String> {
    /**
     * Checks if there are requests for a given advertisement.
     *
     * @param advertisementId the ID of the advertisement to query
     * @return true if there are requests, false otherwise
     */
    @Query("SELECT COUNT(r) > 0 FROM Request r WHERE r.advertisement.id = :advertisementId")
    boolean hasRequests(String advertisementId);
}
