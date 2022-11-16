package com.example.running.Service;

import com.example.running.Bean.Medal;
import com.example.running.Bean.User;
import com.example.running.Repository.MedalRepository;
import com.example.running.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service(value = "MedalService")
public class MedalService {
    @Resource(name = "MedalRepository")
    MedalRepository medalRepository;

    @Resource(name = "UserRepository")
    UserRepository userRepository;

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

    public Integer getMedal(Integer userId, Integer medalId) {
        return medalRepository.insertGetMedalRecord(userId, medalId);
    }
}
