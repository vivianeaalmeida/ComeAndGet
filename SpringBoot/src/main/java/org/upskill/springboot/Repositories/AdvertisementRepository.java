package org.upskill.springboot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upskill.springboot.Models.Advertisement;

public interface AdvertisementRepository  extends JpaRepository<Advertisement, String> {
}
