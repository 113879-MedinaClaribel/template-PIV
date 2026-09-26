package com.example.template.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "status_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(name = "java_version", nullable = false, length = 50)
    private String javaVersion;

    @Column(nullable = false)
    private Instant timestamp;
}
