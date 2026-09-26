package com.example.template.services;

import com.example.template.dtos.StatusResponseDTO;
import com.example.template.services.impl.StatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusServiceTest {

    private StatusService statusService;

    @BeforeEach
    void setUp() {
        statusService = new StatusServiceImpl();
    }

    @Test
    @DisplayName("Should return UP status with non-null version and timestamp")
    void shouldReturnUpStatusWithNonNullVersions() {
        StatusResponseDTO response = statusService.getSystemStatus();

        assertNotNull(response, "Response must not be null");
        assertEquals("UP", response.status());
        assertNotNull(response.javaVersion(), "Java version must not be null");
        assertNotNull(response.timestamp(), "Timestamp must not be null");
    }
}
