package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.DTOs.UserDTO;

import java.util.List;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(String id);

    List<RequestResponseDTO> getRequestsByUserId(String id);
}
