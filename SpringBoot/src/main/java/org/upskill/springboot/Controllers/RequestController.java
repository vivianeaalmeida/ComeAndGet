package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.DTOs.RequestResponseDTO;
import org.upskill.springboot.Services.Interfaces.IRequestService;

import java.util.List;

@RestController
public class RequestController {
    @Autowired
    private IRequestService requestService;

    @GetMapping("/request")
    public ResponseEntity <List<RequestResponseDTO>> getRequest() {
        List<RequestResponseDTO> listResponseDTO = requestService.getRequests();
        return ResponseEntity.ok(listResponseDTO);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<RequestResponseDTO> getRequest(@PathVariable String id) {
        RequestResponseDTO responseDTO = requestService.getRequestById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/request")
    public ResponseEntity<RequestResponseDTO> postRequest(@RequestBody RequestDTO requestDTO) {
        RequestResponseDTO responseDTO = requestService.createRequest(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/request/{id}")
    public ResponseEntity<RequestResponseDTO> putRequest(@PathVariable String id, @RequestBody RequestDTO requestDTO) {
        RequestResponseDTO responseDTO = requestService.updateRequest(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable String id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Deleted.");
    }
}
