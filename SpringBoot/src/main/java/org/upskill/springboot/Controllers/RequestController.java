package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.CategoryDTO;
import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.Services.Interfaces.IRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing requests.
 */
@RestController
public class RequestController extends BaseController {
    @Autowired
    private IRequestService requestService;

    /**
     *
     * @param page Optional parameter for the page number (default is 0)
     * @param size Optional parameter for the page size (default is 10)
     * @return a ResponseEntity containing a CollectionModel of RequestResponseDTO with HATEOAS links,
     *         including self, next, and previous page links and HTTP status OK if retrieval is successful
     */
    @GetMapping("/requests")
    public ResponseEntity<CollectionModel<RequestResponseDTO>> getRequests(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        // Define pagination default values
        int _page = page.orElse(0);
        int _size = page.orElse(10);

        Page<RequestResponseDTO> requestsDTO = requestService.getRequests(_page, _size);

        Link selfLink = linkTo(methodOn(RequestController.class)
                .getRequests(Optional.of(_page), Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (requestsDTO.hasNext()) {
            links.add(linkTo(methodOn(CategoryController.class)
                    .getCategories(Optional.of(_page + 1), Optional.of(_size))).withRel("next"));
        }
        if (requestsDTO.hasPrevious()) {
            links.add(linkTo(methodOn(CategoryController.class)
                    .getCategories(Optional.of(_page - 1), Optional.of(_size))).withRel("previous"));
        }

        return new ResponseEntity<>(CollectionModel.of(requestsDTO.getContent(), links), HttpStatus.OK);
    }

    /**
     * Retrieves a request by its ID.
     *
     * @param id the ID of the request
     * @return a ResponseEntity containing the RequestResponseDTO and HTTP status OK if retrieval is successful
     */
    @GetMapping("/requests/{id}")
    public ResponseEntity<RequestResponseDTO> getRequestById(@PathVariable String id) {
        RequestResponseDTO responseDTO = requestService.getRequestById(id);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Creates a new request.
     *
     * @param requestDTO the RequestDTO object containing the request data
     * @return a ResponseEntity containing the created RequestResponseDTO and HTTP status OK if creation is successful
     */
    @PostMapping("/requests")
    public ResponseEntity<RequestResponseDTO> postRequest(@RequestBody RequestDTO requestDTO) {
        RequestResponseDTO responseDTO = requestService.createRequest(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Updates an existing request by its ID.
     *
     * @param id the ID of the request to update
     * @param requestDTO the RequestDTO object containing the updated request data
     * @return a ResponseEntity containing the updated RequestResponseDTO and HTTP status OK if update is successful
     */
    @PutMapping("/requests/{id}")
    public ResponseEntity<RequestResponseDTO> putRequest(@PathVariable String id, @RequestBody RequestDTO requestDTO) {
        RequestResponseDTO responseDTO = requestService.updateRequest(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Deletes a request by its ID.
     *
     * @param id the ID of the request to delete
     * @return a ResponseEntity containing a confirmation message and HTTP status OK if deletion is successful
     */
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable String id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Deleted.");
    }
}
