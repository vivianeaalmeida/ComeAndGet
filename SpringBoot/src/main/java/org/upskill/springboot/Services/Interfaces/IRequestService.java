package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.DTOs.RequestResponseDTO;

import java.util.List;

public interface IRequestService {
    List<RequestResponseDTO> getRequests();
    RequestResponseDTO getRequestById(String id);
    RequestResponseDTO createRequest(RequestDTO requestDTO);
    RequestResponseDTO updateRequest(String id, RequestDTO requestDTO);

    RequestResponseDTO patchRequest(String id, RequestDTO requestDTO);

    void deleteRequest(String id);
}
