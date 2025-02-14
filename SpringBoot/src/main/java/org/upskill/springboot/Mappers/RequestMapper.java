package org.upskill.springboot.Mappers;

import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.Models.Request;

public class RequestMapper {

    public static RequestDTO toDTO(Request request) {
        RequestDTO requestDTO = new RequestDTO();
        //STATUS
        requestDTO.setClientId(request.getClientId());
        requestDTO.setDate(request.getDate());
        requestDTO.setAdvertisementId(request.getAdvertisement().getId());
        return requestDTO;

    }

    public static Request toEntity(RequestDTO requestDTO) {
        Request request = new Request();
        //STATUS
        request.setClientId(requestDTO.getClientId());
        request.setDate(requestDTO.getDate());
        //request.setAdvertisement(requestDTO.getAdvertisement().);
        return request;
    }

}

//requestDTO.setStatus(request.getStatus());
//requestDTO.setEmail(request.getEmail());
//requestDTO.setPhone(request.setPhone);