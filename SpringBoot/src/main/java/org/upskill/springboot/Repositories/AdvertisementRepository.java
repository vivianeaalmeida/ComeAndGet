package org.upskill.springboot.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.upskill.springboot.Models.Advertisement;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Advertisement} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {
    /**
     * Checks if there are requests for a given advertisement.
     *
     * @param advertisementId the ID of the advertisement to query
     * @return true if there are requests, false otherwise
     */
    @Query("SELECT COUNT(r) > 0 FROM Request r WHERE r.advertisement.id = :advertisementId")
    boolean hasRequests(String advertisementId);


    /**
     * Finds all active advertisements with pagination.
     *
     * @param pageable the pagination information (page number, page size, etc.)
     * @return a page of active advertisements
     */
    @Query("SELECT DISTINCT a FROM Advertisement a WHERE a.status = org.upskill.springboot.Models.Advertisement.AdvertisementStatus.ACTIVE")
    Page<Advertisement> findActiveAdvertisements(Pageable pageable);
}
