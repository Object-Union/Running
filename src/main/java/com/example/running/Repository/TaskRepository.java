package com.example.running.Repository;

import com.example.running.Bean.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "TaskRepository")
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
