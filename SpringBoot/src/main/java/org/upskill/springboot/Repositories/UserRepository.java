package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upskill.springboot.Models.User;

public interface UserRepository extends JpaRepository<User, String> {
}
