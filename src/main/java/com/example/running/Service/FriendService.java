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
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Integer> friendList = user.getFriendList();
            return userRepository.findAllById(friendList);
        }
        return null;
    }

    public List<User> fansList(Integer userId) {
        List<Integer> fansId = userRepository.findFansIdByUserId(userId);
        return userRepository.findAllById(fansId);
    }
}
