package com.example.template.controllers;

import com.example.template.dtos.StatusResponseDTO;
import com.example.template.services.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping({"/", "/api/status"})
    public ResponseEntity<StatusResponseDTO> getStatus() {
        return ResponseEntity.ok(statusService.getSystemStatus());
    }
}
