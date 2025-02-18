package org.upskill.springboot.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Finds all advertisements with the specified status, with pagination.
     *
     * @param status   the status of the advertisements (ACTIVE or CLOSED)
     * @param pageable the pagination information (page number, page size, etc.)
     * @return a page of advertisements with the given status
     */
    Page<Advertisement> findByStatus(Advertisement.AdvertisementStatus status, Pageable pageable);

    /**
     * Finds all advertisements with the specified status
     *
     * @param status   the status of the advertisements (ACTIVE or CLOSED)
     * @return a page of advertisements with the given status
     */
    List<Advertisement> findByStatus(Advertisement.AdvertisementStatus status);

    /**
     * Finds all advertisements by client id, with pagination.
     *
     * @param clientId   the clientId of advertisement
     * @param pageable the pagination information (page number, page size, etc.)
     * @return a page of advertisements with the given status
     */
    Page<Advertisement> findByClientId(String clientId, Pageable pageable);

    @Query("SELECT DISTINCT a FROM Advertisement a " +
            "LEFT JOIN a.item i " +
            "LEFT JOIN i.category c " +
            "WHERE (:municipality IS NULL OR a.municipality = :municipality) " +
            "AND (:keyword IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:category IS NULL OR LOWER(c.designation) LIKE LOWER(CONCAT('%', :category, '%')))")
    Page<Advertisement> searchAdvertisements(@Param("municipality") String municipality,
                                             @Param("keyword") String keyword,
                                             @Param("category") String category,
                                             Pageable pageable);
}
