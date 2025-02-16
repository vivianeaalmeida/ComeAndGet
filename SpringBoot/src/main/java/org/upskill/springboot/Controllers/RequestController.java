package org.upskill.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upskill.springboot.DTOs.RequestDTO;
import org.upskill.springboot.Services.Interfaces.IRequestService;

import java.util.List;

@RestController
public class RequestController {
    @Autowired
    private IRequestService requestService;

    @GetMapping("/request")
    public ResponseEntity <List<RequestDTO>> getRequest() {
        List<RequestDTO> listResponseDTO = requestService.getRequests();
        return ResponseEntity.ok(listResponseDTO);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable String id) {
        RequestDTO responseDTO = requestService.getRequestById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/request")
    public ResponseEntity<RequestDTO> postRequest(@RequestBody RequestDTO requestDTO) {
        RequestDTO responseDTO = requestService.createRequest(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/request/{id}")
    public ResponseEntity<RequestDTO> putRequest(@PathVariable String id, @RequestBody RequestDTO requestDTO) {
        RequestDTO responseDTO = requestService.updateRequest(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/request/{id}")
    public ResponseEntity<RequestDTO> patchRequest(@PathVariable String id, @RequestBody RequestDTO requestDTO) {
        RequestDTO responseDTO = requestService.patchRequest(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }



    @DeleteMapping("/request/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable String id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Deleted.");
    }
}
