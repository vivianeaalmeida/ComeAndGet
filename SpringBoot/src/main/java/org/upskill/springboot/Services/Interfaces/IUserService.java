package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.UserDTO;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(String id);

    Page<ReservationAttemptResponseDTO> getReservationAttemptByUserId(String id, int page, int size);

    Page<ReservationAttemptResponseDTO> getReservationAttemptFromAdvertisementOfUser(String id, int page, int size);

}
