package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Exceptions.NotFoundException;
import org.upskill.springboot.Mappers.UserMapper;
import org.upskill.springboot.Models.User;
import org.upskill.springboot.Repositories.UserRepository;
import org.upskill.springboot.Services.Interfaces.IUserService;

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
     * @throws NotFoundException if the user is not found
     */
    @Override
    public UserDTO getUserById(String id) {
        return this.userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
    }

}
