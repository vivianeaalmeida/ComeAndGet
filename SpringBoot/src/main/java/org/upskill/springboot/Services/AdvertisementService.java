package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Exceptions.AdvertisementValidationException;
import org.upskill.springboot.Exceptions.InvalidLengthException;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.AdvertisementRepository;
import org.upskill.springboot.Repositories.ItemRepository;
import org.upskill.springboot.Services.Interfaces.IAdvertisementService;

import java.time.LocalDate;

@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    //    Advertisement Status is "active" by default.
    //    Initial Date is automatically created with the current date.
    //    The advertisement must provide the item details when creating an advertisement.
    //    The system must create the associated item together with the advertisement.
    //    The advertisement must not exist without an associated item.

    @Override
    @Transactional // Garante que a operação seja atômica
    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO) {
        // Validate the advertisement
        validateAdvertisement(advertisementDTO);

        Advertisement advertisement = AdvertisementMapper.toEntity(advertisementDTO);
        advertisement.setInitialDate(LocalDate.now());
        advertisement.setStatus(Advertisement.AdvertisementStatus.ACTIVE);

        // Create and validate the Item
        Item item = advertisementDTO.getItem();
        itemService.validateItem(ItemMapper.toDTO(item)); // Valida o Item antes de salvar
        ItemDTO itemDTO = itemService.createItem(ItemMapper.toDTO(item));
        // Associate the item with the advertisement
        advertisement.setItem(item);

        // Save advertisement in the database
        advertisement = advertisementRepository.save(advertisement);

        return AdvertisementMapper.toDTO(advertisement);
    }

    @Override
    public Page<AdvertisementDTO> getAllAdvertisements(int page, int size) {
        return null;
    }

    @Override
    public AdvertisementDTO getAdvertisementById(long id) {
        return null;
    }

    @Override
    public AdvertisementDTO updateAdvertisement(long id, AdvertisementDTO advertisementDto) {
        return null;
    }

    @Override
    public AdvertisementDTO deleteAdvertisement(long id) {
        return null;
    }


    public boolean validateAdvertisement(AdvertisementDTO advertisementDTO) {
        // Check if the title is less than 5 or more than 50 characters
        if (advertisementDTO.getTitle().length() < 5 || advertisementDTO.getTitle().length() > 50) {
            throw new InvalidLengthException("The title must have between 5 and 50 characters.");
        }

        // Check if the title is less than 5 or more than 50 characters
        if (advertisementDTO.getDescription().length() < 5 || advertisementDTO.getDescription().length() > 50) {
            throw new InvalidLengthException("The description must have between 5 and 50 characters.");
        }

        // Check if the client associated with the advertisement is valid
        if (advertisementDTO.getClientId() == null) {
            throw new AdvertisementValidationException("The client ID must be provided.");
        }

        return true;
    }
}
