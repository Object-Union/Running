package com.example.running.Controller;

import com.example.running.Bean.Task;
import com.example.running.Bean.UserProcess;
import com.example.running.Service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Task")
public class TaskController {
    @Resource(name = "TaskService")
    TaskService taskService;

    @RequestMapping("/GetAllTasks")
    public List<Task> GetAllTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping("/GetUserProcess")
    public List<UserProcess> GetUserProcess(Integer userId) {
        return taskService.getUserProcess(userId);
    }
}
