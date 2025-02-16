package org.upskill.springboot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.DTOs.UserDTO;
import org.upskill.springboot.Exceptions.AdvertisementValidationException;
import org.upskill.springboot.Exceptions.NotNullException;
import org.upskill.springboot.Exceptions.RequestNotFoundException;
import org.upskill.springboot.Mappers.AdvertisementMapper;
import org.upskill.springboot.Mappers.RequestMapper;
import org.upskill.springboot.Mappers.UserMapper;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Request;
import org.upskill.springboot.Models.User;
import org.upskill.springboot.Repositories.RequestRepository;
import org.upskill.springboot.Services.Interfaces.IRequestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to Request entities.
 * Provides methods for creating, updating, deleting, and retrieving Requests.
 */
@Service
public class RequestService implements IRequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all requests from the database.
     *
     * @return a list of RequestDTO objects representing all requests
     */
    @Override
    public List<RequestDTO> getRequests() {
        List<Request> requests = requestRepository.findAll();
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for (Request request : requests) {
            requestDTOs.add(RequestMapper.toDTO(request));
        }
        return requestDTOs;
    }

    /**
     * Retrieves a specific request by its ID.
     *
     * @param id the ID of the request to retrieve
     * @return the RequestDTO object corresponding to the requested ID
     * @throws RequestNotFoundException if no request is found with the given ID
     */
    @Override
    public RequestDTO getRequestById(String id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + id));
        return RequestMapper.toDTO(request);
    }

    /**
     * Creates a new request with the provided RequestDTO data.
     *
     * @param requestDTO the RequestDTO object containing the details of the new request
     * @return the created RequestDTO object
     * @throws AdvertisementValidationException if the advertisement associated with the request is not active
     */
    @Override
    public RequestDTO createRequest(RequestDTO requestDTO) {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(requestDTO.getAdvertisementId());
        UserDTO userDTO = userService.getUserById(requestDTO.getUserId());
        if (advertisementDTO.getStatus().equals(Advertisement.AdvertisementStatus.ACTIVE.toString())) {
            Request request = RequestMapper.toEntity(requestDTO);
            Advertisement adEntity = AdvertisementMapper.toEntity(advertisementDTO);
            request.setAdvertisement(adEntity);
            User userEntity = UserMapper.toEntity(userDTO);
            request.setUser(userEntity);
            request.setDate(LocalDate.now());
            return RequestMapper.toDTO(requestRepository.save(request));
        } else {
            throw new AdvertisementValidationException("Advertisement is not active.");
        }
    }

    /**
     * Updates the request with the provided ID with new data.
     *
     * @param id the ID of the request to update
     * @param requestDTO the RequestDTO object containing the updated request data
     * @return the updated RequestDTO object
     * @throws RequestNotFoundException if the request with the given ID does not exist
     * @throws NotNullException if the status of the request is null
     */
    @Override
    public RequestDTO updateRequest(String id, RequestDTO requestDTO) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            if (requestDTO.getStatus() != null) {
                request.setStatus(Request.RequestStatus.valueOf(requestDTO.getStatus().toUpperCase()));
            } else {
                throw new NotNullException("Status cannot be null.");
            }
            setUser(requestDTO, request);
            setAdvertisement(requestDTO, request);
            return RequestMapper.toDTO(requestRepository.save(request));
        } else {
            throw new RequestNotFoundException("Request not found with id: " + id);
        }
    }

    /**
     * Partially updates the request with the provided ID with new data.
     *
     * @param id the ID of the request to update
     * @param requestDTO the RequestDTO object containing the updated request data
     * @return the partially updated RequestDTO object
     * @throws RequestNotFoundException if the request with the given ID does not exist
     */
    @Override
    public RequestDTO patchRequest(String id, RequestDTO requestDTO) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            if (requestDTO.getStatus() != null) {
                request.setStatus(Request.RequestStatus.valueOf(requestDTO.getStatus().toUpperCase()));
            }
            if (requestDTO.getUserId() != null) {
                setUser(requestDTO, request);
            }
            if (requestDTO.getAdvertisementId() != null) {
                setAdvertisement(requestDTO, request);
            }
            return RequestMapper.toDTO(requestRepository.save(request));
        } else {
            throw new RequestNotFoundException("Request not found with id: " + id);
        }
    }

    /**
     * Deletes the request with the given ID.
     *
     * @param id the ID of the request to delete
     * @throws RequestNotFoundException if the request with the given ID does not exist
     */
    @Override
    public void deleteRequest(String id) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            requestRepository.delete(requestOpt.get());
        } else {
            throw new RequestNotFoundException("Request not found with id: " + id);
        }
    }

    /**
     * Sets the advertisement for the request using the provided RequestDTO.
     *
     * @param requestDTO the RequestDTO object containing the advertisement ID
     * @param request the Request entity to update with the advertisement
     */
    private void setAdvertisement(RequestDTO requestDTO, Request request) {
        AdvertisementDTO adDTO = advertisementService.getAdvertisementById(requestDTO.getAdvertisementId());
        request.setAdvertisement(AdvertisementMapper.toEntity(adDTO));
    }

    /**
     * Sets the user for the request using the provided RequestDTO.
     *
     * @param requestDTO the RequestDTO object containing the user ID
     * @param request the Request entity to update with the user
     */
    private void setUser(RequestDTO requestDTO, Request request) {
        UserDTO userDTO = userService.getUserById(requestDTO.getUserId());
        request.setUser(UserMapper.toEntity(userDTO));
    }
}
