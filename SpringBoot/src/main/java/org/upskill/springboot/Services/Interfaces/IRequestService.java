package org.upskill.springboot.Services.Interfaces;

import org.upskill.springboot.DTOs.RequestDTO;

import java.util.List;

public interface IRequestService {
    List<RequestDTO> getRequests();
    RequestDTO getRequestById(String id);
    RequestDTO createRequest(RequestDTO requestDTO);
    RequestDTO updateRequest(String id, RequestDTO requestDTO);

    RequestDTO patchRequest(String id, RequestDTO requestDTO);

    void deleteRequest(String id);
}
