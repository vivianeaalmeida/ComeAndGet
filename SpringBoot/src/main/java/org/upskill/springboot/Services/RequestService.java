package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.Exceptions.RequestNotFoundException;
import org.upskill.springboot.Mappers.RequestMapper;
import org.upskill.springboot.Models.Request;
import org.upskill.springboot.Repositories.RequestRepository;
import org.upskill.springboot.Services.Interfaces.IRequestService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService implements IRequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<RequestDTO> getRequests() {
        List<Request> requests = requestRepository.findAll();
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for (Request request : requests) {
            requestDTOs.add(RequestMapper.toDTO(request));
        }
        return requestDTOs;
    }
    @Override
    public RequestDTO getRequestById(String id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + id));
        return RequestMapper.toDTO(request);
    }

    @Override
    public RequestDTO createRequest(RequestDTO requestDTO) {
        //todo: validate advertisement
        Request request = RequestMapper.toEntity(requestDTO);
        return RequestMapper.toDTO(requestRepository.save(request));
    }

    @Override
    public RequestDTO updateRequest(String id, RequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteRequest(String id) {

    }
}
