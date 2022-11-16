package com.example.running.Controller;

import com.example.running.Bean.User;
import com.example.running.Service.FriendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Friend")
public class FriendController {
    @Resource(name = "FriendService")
    FriendService friendService;

    @RequestMapping("/SubscribeList")
    public List<User> SubscribeList(Integer userId) {
        return friendService.subscribeList(userId);
    }

    @RequestMapping("/Subscribe")
    public Integer Subscribe(Integer userId, Integer friendId) {
        return friendService.subscribe(userId, friendId);
    }

    @RequestMapping("/UnSubscribe")
    public Integer UnSubscribe(Integer userId, Integer friendId) {
        return friendService.unSubscribe(userId, friendId);
    }
}
