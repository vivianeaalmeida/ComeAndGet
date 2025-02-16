package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.Models.Request;

public class RequestMapper {

    public static RequestDTO toDTO(Request request) {
        RequestDTO requestDTO = new RequestDTO();
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
        request.setDate(requestDTO.getDate());
        return request;
    }

}

//requestDTO.setStatus(request.getStatus());
//requestDTO.setEmail(request.getEmail());
//requestDTO.setPhone(request.setPhone);