package com.example.running.Service;

import com.example.running.Bean.User;
import com.example.running.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service(value = "FriendService")
public class FriendService {
    @Resource(name = "UserRepository")
    UserRepository userRepository;

    public Integer subscribe(Integer userId, Integer friendId) {
        return userRepository.insertASubscribeRecord(userId, friendId);
    }

    public Integer unSubscribe(Integer userId, Integer friendId) {
        return userRepository.deleteASubscribeRecord(userId, friendId);
    }

    public List<User> subscribeList(Integer userId) {
        List<Integer> fansId = userRepository.findSubscribeIdByUserId(userId);
        return userRepository.findAllById(fansId);
    }

    public List<User> fansList(Integer userId) {
        List<Integer> fansId = userRepository.findFansIdByUserId(userId);
        return userRepository.findAllById(fansId);
    }
}
