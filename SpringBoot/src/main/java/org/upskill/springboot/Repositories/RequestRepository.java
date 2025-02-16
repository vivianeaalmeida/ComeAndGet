package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.upskill.springboot.Models.Request;


@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

}
