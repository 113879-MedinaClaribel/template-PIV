package com.example.template.services;

import com.example.template.dtos.StatusResponseDTO;
import com.example.template.entities.StatusLogEntity;
import com.example.template.repositories.StatusLogRepository;
import com.example.template.services.impl.StatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StatusServiceTest {

    @Mock
    private StatusLogRepository statusLogRepository;

    private StatusService statusService;

    @BeforeEach
    void setUp() {
        statusService = new StatusServiceImpl(statusLogRepository);
    }

    @Test
    @DisplayName("Should return UP status and persist audit log entry")
    void shouldReturnUpStatusAndPersistLog() {
        StatusResponseDTO response = statusService.getSystemStatus();

        assertNotNull(response, "Response must not be null");
        assertEquals("UP", response.status());
        assertNotNull(response.javaVersion(), "Java version must not be null");
        assertNotNull(response.timestamp(), "Timestamp must not be null");

        // Verify that the persistence contract was executed
        verify(statusLogRepository).save(any(StatusLogEntity.class));
    }
}
