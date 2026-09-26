package com.example.template.dtos;

import java.time.Instant;

public record StatusResponseDTO(
        String status,
        String message,
        String javaVersion,
        String springBootVersion,
        Instant timestamp
) {}
