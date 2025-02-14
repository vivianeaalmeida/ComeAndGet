package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upskill.springboot.Models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
