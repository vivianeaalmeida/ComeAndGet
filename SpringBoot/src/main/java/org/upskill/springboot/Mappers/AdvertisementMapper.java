package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.Models.Advertisement;

public class AdvertisementMapper {

    public static AdvertisementDTO toDTO(Advertisement advertisement) {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();
        advertisementDTO.setId(advertisement.getId());
        advertisementDTO.setTitle(advertisement.getTitle());
        advertisementDTO.setDescription(advertisement.getDescription());
        advertisementDTO.setInitialDate(advertisement.getInitialDate());
        advertisementDTO.setStatus(advertisement.getStatus());
        advertisementDTO.setItem(advertisement.getItem());
        advertisementDTO.setClientId(advertisement.getClientId());

        return advertisementDTO;
    }


    public static Advertisement toEntity(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementDTO.getId());
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setDescription(advertisementDTO.getDescription());
        advertisement.setInitialDate(advertisementDTO.getInitialDate());
        advertisement.setStatus(advertisementDTO.getStatus());
        advertisement.setItem(advertisementDTO.getItem());
        advertisement.setClientId(advertisementDTO.getClientId());

        return advertisement;
    }
}
