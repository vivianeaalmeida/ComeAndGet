package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.upskill.springboot.DTOs.ItemDTO;
import org.upskill.springboot.Exceptions.CategoryNotFoundException;
import org.upskill.springboot.Exceptions.InvalidFileExtensionException;
import org.upskill.springboot.Exceptions.ItemValidationException;
import org.upskill.springboot.Mappers.CategoryMapper;
import org.upskill.springboot.Mappers.ItemMapper;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Repositories.ItemRepository;
import org.upskill.springboot.Services.Interfaces.IItemService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Service class for managing items.
 */
@Service
public class  ItemService implements IItemService {

    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";

    /**
     * The item repository.
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * The category repository.
     */
    @Autowired
    CategoryService categoryService;

    /**
     * Creates a new item.
     *
     * @param itemDTO the item data transfer object
     * @return the created item data transfer object
     */
    @Override
    public ItemDTO createItem(ItemDTO itemDTO) {
        validateItem(itemDTO);

        Item item = ItemMapper.toEntity(itemDTO);
        item = this.itemRepository.save(item);

        return ItemMapper.toDTO(item);
    }

    /**
     * Validates the item data transfer object.
     *
     * @param itemDTO the item data transfer object
     * @throws IllegalArgumentException if the item is null
     * @throws ItemValidationException   if the image URL, condition, or category ID is not valid
     */
    public void validateItem(ItemDTO itemDTO) {
        // Item validation
        if (itemDTO == null) {
            throw new IllegalArgumentException("The item must be provided.");
        }

        // Condition validation
        if (itemDTO.getCondition() == null) {
            throw new ItemValidationException("The condition must be provided.");
        }

        // Category validation
        if (itemDTO.getCategory().getId() == null) {
            throw new ItemValidationException("The category ID must be provided.");
        }

        // Validate category by calling the CategoryService and throws different exception
        try {
            Category category = categoryService.getCategoryById(itemDTO.getCategory().getId());
            itemDTO.setCategory(CategoryMapper.toDTO(category));
            itemDTO.setCategory(CategoryMapper.toDTO(category));
        } catch (CategoryNotFoundException e) {
            // Catch the exception and throw a more appropriate exception for validation
            throw new ItemValidationException("Category with ID " + itemDTO.getCategory().getId() + " is invalid.");
        }
    }

    /**
     * Checks if there are items associated with a specific category.
     *
     * @param categoryId The identifier of the category.
     * @return {@code true} if there are items associated with the category, {@code false} otherwise.
     */
    public boolean hasItemsInCategory(String categoryId) {
        return itemRepository.existsByCategory_Id(categoryId);
    }


    /**
     * Uploads an image file to the server, ensuring that the file is not empty and the directory exists.
     * The method generates a unique file name for the image and saves it to a predefined directory.
     *
     * @param file the image file to be uploaded (must not be null or empty)
     * @return the relative file path of the uploaded image (to store in the database)
     * @throws IOException if there is an error while saving the file to the server
     */
    public String uploadItemImage(MultipartFile file) throws IOException{
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("The image file cannot be empty.");
        }

        // Check if the image exceeds the size limit (5MB)
        if (file.getSize() > 5 * 1024 * 1024) {  // 5MB em bytes
            throw new MaxUploadSizeExceededException(5 * 1024 * 1024);
        }

        // Ensures that the directory exists
        File uploadDir = new File(IMAGE_UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Creates the necessary directories
        }

        // Generates a unique name for the image
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

        // Defines the full path where the image will be saved
        Path filePath = Paths.get(IMAGE_UPLOAD_DIR, uniqueFileName);
        Files.write(filePath, file.getBytes());

        // Returns the relative path of the image to store in the database
        return IMAGE_UPLOAD_DIR + uniqueFileName;
    }


    /**
     * Auxiliary method to extract the file extension from a given file name.
     * This method checks if the file name is valid (not null and contains an extension).
     * If the file name is invalid, an exception is thrown.
     *
     * @param fileName the name of the file from which the extension will be extracted
     * @return the file extension (everything after the last dot in the file name)
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            throw new InvalidFileExtensionException("Invalid file name.");
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
