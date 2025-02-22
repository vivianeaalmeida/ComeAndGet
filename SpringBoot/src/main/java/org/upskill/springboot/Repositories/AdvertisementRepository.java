package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.upskill.springboot.Models.Advertisement;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Advertisement} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {

    /**
     * Finds all advertisements except those with the specified status
     *
     * @param status the status to be excluded from the results
     * @return a list of advertisements that do not have the specified status
     */
    List<Advertisement> findByStatusNot(Advertisement.AdvertisementStatus status);

    /**
     * Finds all advertisements with the specified status
     *
     * @param status the status of the advertisements
     * @return a list of advertisements with the given status
     */
    List<Advertisement> findByStatus(Advertisement.AdvertisementStatus status);

    /**
     * Finds advertisements by client ID, excluding ones with a specificied status.
     *
     * @param clientId the ID of the client
     * @return a list of active advertisements for the given client
     */
    List<Advertisement> findByClientIdAndStatusNot(String clientId, Advertisement.AdvertisementStatus status);

    /**
     * Searches for advertisements by municipality, keyword, and category.
     * Only advertisements with a status of 0 (ACTIVE) are considered.
     *
     * @param municipality the municipality filter (nullable).
     * @param keyword searches in title and description (nullable).
     * @param category the category filter (nullable).
     * @return a list of matching advertisements.
     */
    @Query("SELECT DISTINCT a FROM Advertisement a " +
            "LEFT JOIN a.item i " +
            "LEFT JOIN i.category c " +
            "WHERE a.status = 0" +
            "AND (:municipality IS NULL OR a.municipality = :municipality) " +
            "AND (:keyword IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:category IS NULL OR LOWER(c.designation) LIKE LOWER(CONCAT('%', :category, '%')))")
    List<Advertisement> searchAdvertisements(@Param("municipality") String municipality,
                                             @Param("keyword") String keyword,
                                             @Param("category") String category);
}
