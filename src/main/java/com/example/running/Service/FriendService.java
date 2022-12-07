package com.example.running.Service;

import com.example.running.Bean.User;
import com.example.running.Common.TaskCommon;
import com.example.running.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "FriendService")
public class FriendService {
    @Resource(name = "UserRepository")
    UserRepository userRepository;

    @Resource(name = "MedalService")
    MedalService medalService;

    @Transactional
    public Integer subscribe(Integer userId, Integer friendId) {
        medalService.getMedalOrNot(userId, TaskCommon.SUBSCRIBE_5_USERS, 1);
        medalService.getMedalOrNot(friendId, TaskCommon.GET_3_FANS, 1);
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
