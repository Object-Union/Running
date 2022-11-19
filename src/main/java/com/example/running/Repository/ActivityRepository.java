package com.example.running.Repository;

import com.example.running.Bean.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "ActivityRepository")
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}
