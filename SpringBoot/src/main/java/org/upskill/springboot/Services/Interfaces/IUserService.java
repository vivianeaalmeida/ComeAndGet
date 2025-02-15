package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.UserDTO;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(String id);
}
