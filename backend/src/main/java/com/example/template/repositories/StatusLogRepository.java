package com.example.template.repositories;

import com.example.template.entities.StatusLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusLogRepository extends JpaRepository<StatusLogEntity, Long> {
    List<StatusLogEntity> findTop10ByOrderByTimestampDesc();
}
