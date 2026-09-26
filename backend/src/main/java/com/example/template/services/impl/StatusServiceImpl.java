package com.example.template.services.impl;

import com.example.template.dtos.StatusResponseDTO;
import com.example.template.entities.StatusLogEntity;
import com.example.template.repositories.StatusLogRepository;
import com.example.template.services.StatusService;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusLogRepository statusLogRepository;

    public StatusServiceImpl(StatusLogRepository statusLogRepository) {
        this.statusLogRepository = statusLogRepository;
    }

    @Override
    @Transactional
    public StatusResponseDTO getSystemStatus() {
        String javaVersion = System.getProperty("java.version");
        String springVersion = SpringBootVersion.getVersion();
        Instant now = Instant.now();

        // Corroborate persistence integrity by saving an audit log entry
        StatusLogEntity logEntity = StatusLogEntity.builder()
                .status("UP")
                .message("Backend operating successfully with decoupled architecture")
                .javaVersion(javaVersion)
                .timestamp(now)
                .build();
        statusLogRepository.save(logEntity);

        return new StatusResponseDTO(
                "UP",
                "Backend operating successfully with decoupled architecture",
                javaVersion,
                springVersion,
                now
        );
    }
}
