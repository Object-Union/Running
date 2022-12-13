package com.example.running.Service;

import com.example.running.Bean.Medal;
import com.example.running.Bean.Task;
import com.example.running.Bean.User;
import com.example.running.Bean.UserProcess;
import com.example.running.Repository.MedalRepository;
import com.example.running.Repository.TaskRepository;
import com.example.running.Repository.UserProcessRepository;
import com.example.running.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service(value = "MedalService")
public class MedalService {
    @Resource(name = "MedalRepository")
    MedalRepository medalRepository;

    @Resource(name = "UserRepository")
    UserRepository userRepository;

    @Resource(name = "TaskRepository")
    TaskRepository taskRepository;

    @Resource(name = "UserProcessRepository")
    UserProcessRepository userProcessRepository;

    public List<Medal> info() {
        return medalRepository.findAll();
    }

    public List<Integer> personal(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getMedalList();
        }
        return null;
    }

    @Transactional
    public void getMedalOrNot(Integer userId, Integer taskId, Integer delta) {
        Task task = taskRepository.findTaskById(taskId);
        assert task != null;
        List<Integer> medalIdList = medalRepository.findUserGetMedalIdList(userId);
        if (!medalIdList.contains(task.getMedalId())) {
            userProcessRepository.updateUserProgress(userId, taskId, delta);
            UserProcess userProcess = userProcessRepository.findUserProcessByUserIdAndTaskId(userId, taskId);
            if (task.getTarget() <= userProcess.getProcess()) {
                medalRepository.insertGetMedalRecord(userId, task.getMedalId());
            }
        }
    }
}
