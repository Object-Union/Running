package com.example.running.Service;

import com.example.running.Bean.Task;
import com.example.running.Bean.UserProcess;
import com.example.running.Repository.TaskRepository;
import com.example.running.Repository.UserProcessRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "TaskService")
public class TaskService {
    @Resource(name = "TaskRepository")
    TaskRepository taskRepository;

    @Resource(name = "UserProcessRepository")
    UserProcessRepository userProcessRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<UserProcess> getUserProcess(Integer userId) {
        return userProcessRepository.findUserProcessByUserId(userId);
    }
}
