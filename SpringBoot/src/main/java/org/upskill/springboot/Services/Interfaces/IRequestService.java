package org.upskill.springboot.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.DTOs.RequestStatusDTO;

import java.util.List;

/**
 * Service interface for managing requests.
 */
public interface IRequestService {

    /**
     * Retrieves all requests.
     *
     * @return a list of all request response data transfer objects
     */
    Page<RequestResponseDTO> getRequests(int page, int size);

    /**
     * Retrieves a specific request by its ID.
     *
     * @param id the ID of the request
     * @return the request response data transfer object corresponding to the given ID
     */
    RequestResponseDTO getRequestById(String id);

    /**
     * Creates a new request.
     *
     * @param requestDTO the request data transfer object containing the details of the new request
     * @return the created request response data transfer object
     */
    RequestResponseDTO createRequest(RequestDTO requestDTO);

    /**
     * Updates an existing request.
     *
     * @param id           the ID of the request to be updated
     * @param requestDTO   the request data transfer object containing the updated details
     * @return the updated request response data transfer object
     */
    RequestResponseDTO updateRequest(String id, RequestDTO requestDTO);

    /**
     * Partially updates a request.
     *
     * @param id           the ID of the request to be partially updated
     * @param idAdvertisement  the ID of the advertisement
     * @param requestStatusDTO   the object with the new status
     * @return the partially updated request response data transfer object
     */
    RequestResponseDTO patchRequest(String id, String idAdvertisement, RequestStatusDTO requestStatusDTO);

    /**
     * Deletes a request by its ID.
     *
     * @param id the ID of the request to be deleted
     */
    void deleteRequest(String id);

}
