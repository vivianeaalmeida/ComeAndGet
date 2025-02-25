package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.Models.Advertisement;

/**
 * Mapper class for converting {@link Advertisement} entities to {@link AdvertisementDTO} objects and vice versa.
 */
public class AdvertisementMapper {

    /**
     * Converts an {Advertisement entity to an AdvertisementDTO object.
     *
     * @param advertisement The Advertisement entity to be converted.
     * @return The AdvertisementDTO object created from the Advertisement entity.
     */
    public static AdvertisementDTO toDTO(Advertisement advertisement) {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();
        advertisementDTO.setId(advertisement.getId());
        advertisementDTO.setTitle(advertisement.getTitle());
        advertisementDTO.setDescription(advertisement.getDescription());
        advertisementDTO.setDate(advertisement.getDate());
        advertisementDTO.setStatus(advertisement.getStatus().toString());
        advertisementDTO.setItem(ItemMapper.toDTO(advertisement.getItem()));
        advertisementDTO.setClientId(advertisement.getClientId());

        return advertisementDTO;
    }

    /**
     * Converts an AdvertisementDTO object to an Advertisement entity.
     *
     * @param advertisementDTO The AdvertisementDTO object to be converted.
     * @return The Advertisement entity created from the AdvertisementDTO object.
     */
    public static Advertisement toEntity(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementDTO.getId());
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setDescription(advertisementDTO.getDescription());
        advertisement.setDate(advertisementDTO.getDate());

        // Converts String to Enum
        if (advertisementDTO.getStatus() != null) {
            advertisement.setStatus(Advertisement.AdvertisementStatus.valueOf(advertisementDTO.getStatus().toUpperCase()));
        }

        advertisement.setItem(ItemMapper.toEntity(advertisementDTO.getItem()));
        advertisement.setClientId(advertisementDTO.getClientId());

        return advertisement;
    }
}