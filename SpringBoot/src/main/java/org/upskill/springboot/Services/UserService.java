package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Exceptions.AdvertisementNotFoundException;
import org.upskill.springboot.Mappers.UserMapper;
import org.upskill.springboot.Models.User;
import org.upskill.springboot.Repositories.UserRepository;
import org.upskill.springboot.Services.Interfaces.IUserService;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
public class UserService implements IUserService {
    /**
     * The user repository.
     */
    @Autowired
    private UserRepository userRepository;

    private final RequestService requestService;

    public UserService(@Lazy RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * Creates a new user.
     *
     * @param userDTO the user data transfer object
     * @return the created user data transfer object
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        user = userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the user ID
     * @return the user data transfer object
     * @throws AdvertisementNotFoundException if the user is not found
     */
    @Override
    public UserDTO getUserById(String id) {
        return this.userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new AdvertisementNotFoundException("User with id " + id + " not found."));
    }

    /**
     * Retrieves a list of request DTOs for a given user ID by delegating the call to the request service.
     *
     * @param id the unique identifier of the user
     * @param page Optional parameter for the page number (default is 0)
     * @param size Optional parameter for the page size (default is 10)
     * @return a list of {@link RequestResponseDTO} objects representing the user's requests
     */
    @Override
    public Page<RequestResponseDTO> getRequestsByUserId(String id,int page, int size){

        return this.requestService.getRequestsByUserId(id, page, size);
    }

    @Override
    public Page<RequestResponseDTO> getRequestsFromAdvertisementOfUser(String id,int page, int size) {

        return requestService.getRequestsFromAdvertisementOfUser(id, page, size);
    }


}
