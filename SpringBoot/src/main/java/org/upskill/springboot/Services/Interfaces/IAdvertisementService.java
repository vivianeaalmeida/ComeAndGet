package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.AdvertisementDTO;

public interface IAdvertisementService {

    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO);

    Page<AdvertisementDTO> getAllAdvertisements(int page, int size);

    AdvertisementDTO getAdvertisementById(long id);

    AdvertisementDTO updateAdvertisement (long id, AdvertisementDTO advertisementDto);

    AdvertisementDTO deleteAdvertisement (long id);
}
