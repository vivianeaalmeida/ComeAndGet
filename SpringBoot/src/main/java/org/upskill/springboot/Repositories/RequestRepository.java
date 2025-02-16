package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.upskill.springboot.Models.Request;

/**
 * Repository interface for performing CRUD operations on {@link Request} entities.
 * Extends {@link JpaRepository} to leverage built-in methods for data access.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

}
