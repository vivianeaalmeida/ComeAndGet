package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Exceptions.NotFoundException;
import org.upskill.springboot.Mappers.UserMapper;
import org.upskill.springboot.Models.User;
import org.upskill.springboot.Repositories.UserRepository;
import org.upskill.springboot.Services.Interfaces.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        user = userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(String id) {
        return this.userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
    }

}
