package com.example.template.dtos;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDTO(
        int statusCode,
        String error,
        String message,
        String path,
        List<String> details,
        Instant timestamp
) {}
