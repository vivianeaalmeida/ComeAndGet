package org.upskill.springboot.Mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.Models.Advertisement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting {@link Advertisement} entities to {@link AdvertisementDTO} objects and vice versa.
 */
public class AdvertisementMapper {

    /**
     * Converts an {@link Advertisement} entity to an {@link AdvertisementDTO} object.
     *
     * @param advertisement The {@link Advertisement} entity to be converted.
     * @return The {@link AdvertisementDTO} object created from the {@link Advertisement} entity.
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
     * Converts an {@link AdvertisementDTO} object to an {@link Advertisement} entity.
     *
     * @param advertisementDTO The {@link AdvertisementDTO} object to be converted.
     * @return The {@link Advertisement} entity created from the {@link AdvertisementDTO} object.
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

    public static Page<AdvertisementDTO> toDTO(Page<Advertisement> advertisements) {
        List<AdvertisementDTO> advertisementDTOS = advertisements.stream().map(AdvertisementMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(advertisementDTOS, advertisements.getPageable(), advertisements.getTotalElements());
    }
}