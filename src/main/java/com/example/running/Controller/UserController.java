package com.example.running.Controller;

import com.example.running.Bean.RunRecord;
import com.example.running.Bean.User;
import com.example.running.Service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Resource(name = "UserService")
    UserService userService;

    @RequestMapping("/Register")
    public User Register(User user) {
        return userService.register(user);
    }

    @RequestMapping("/Login")
    public User Login(String username, String password) {
        return userService.login(username, password);
    }

    @RequestMapping("/UploadAvatar")
    public void UploadAvatar(@RequestBody MultipartFile avatar, @RequestParam("userId") Integer userId) throws IOException {
        userService.updateAvatar(avatar, userId);
    }

    @RequestMapping("/SearchUser")
    public List<User> SearchUser(String nickName) {
        return userService.searchUser(nickName);
    }

    @RequestMapping("/GetUser")
    public User GetUser(Integer userId) {
        return userService.getUser(userId);
    }

    @RequestMapping("/Record")
    public List<RunRecord> Record(Integer userId) {
        return userService.record(userId);
    }
}
