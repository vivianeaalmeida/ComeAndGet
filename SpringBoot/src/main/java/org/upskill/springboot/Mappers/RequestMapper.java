package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.Models.Request;

public class RequestMapper {

    public static RequestResponseDTO toDTO(Request request) {
        RequestResponseDTO requestDTO = new RequestResponseDTO();
        requestDTO.setId(request.getId());
        requestDTO.setStatus(request.getStatus().toString());
        requestDTO.setUserId(request.getUser().getId());
        requestDTO.setDate(request.getDate());
        requestDTO.setAdvertisementId(request.getAdvertisement().getId());
        return requestDTO;

    }

    public static Request toEntity(RequestDTO requestDTO) {
        Request request = new Request();
        if(requestDTO.getStatus() != null) {
            request.setStatus(Request.RequestStatus.valueOf(requestDTO.getStatus().toUpperCase()));
        }
        return request;
    }

}

//requestDTO.setStatus(request.getStatus());
//requestDTO.setEmail(request.getEmail());
//requestDTO.setPhone(request.setPhone);