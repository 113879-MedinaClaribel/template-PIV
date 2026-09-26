package com.example.template.services.impl;

import com.example.template.dtos.StatusResponseDTO;
import com.example.template.services.StatusService;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StatusServiceImpl implements StatusService {

    @Override
    public StatusResponseDTO getSystemStatus() {
        String javaVersion = System.getProperty("java.version");
        String springVersion = SpringBootVersion.getVersion();

        return new StatusResponseDTO(
                "UP",
                "Backend operating successfully with decoupled architecture",
                javaVersion,
                springVersion,
                Instant.now()
        );
    }
}
