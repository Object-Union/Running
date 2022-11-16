package com.example.running.Repository;

import com.example.running.Bean.DriftRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "DriftRunRepository")
public interface DriftRunRepository extends JpaRepository<DriftRun, Integer> {
}
