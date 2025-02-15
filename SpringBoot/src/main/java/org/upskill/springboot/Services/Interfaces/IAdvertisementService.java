package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.AdvertisementDTO;

public interface IAdvertisementService {

    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO);

    Page<AdvertisementDTO> getAllAdvertisements(int page, int size);

    Page<AdvertisementDTO> getActiveAdvertisements(int page, int size);

    AdvertisementDTO getAdvertisementById(String id);

    AdvertisementDTO updateAdvertisement (String id, AdvertisementDTO advertisementDto);

    AdvertisementDTO deleteAdvertisement (String id);
}
