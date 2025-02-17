package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.DTOs.UserDTO;


import java.util.List;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(String id);

    Page<RequestResponseDTO> getRequestsByUserId(String id,int page, int size);

    Page<RequestResponseDTO> getRequestsFromAdvertisementOfUser(String id,int page, int size);

}
