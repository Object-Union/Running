package com.example.running.Service;

import com.example.running.Bean.RunRecord;
import com.example.running.Bean.User;
import com.example.running.Common.Upload;
import com.example.running.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service(value = "UserService")
public class UserService {
    @Resource(name = "UserRepository")
    UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public void updateAvatar(MultipartFile avatar, Integer userId) throws IOException {
        if (avatar != null) {
            String uri = userId + ".png";
            avatar.transferTo(new File(Upload.AVATAR_DIR + uri));
            userRepository.updateAvatar(Upload.BASE_URL + uri, userId);
        }
    }

    public List<User> searchUser(String nickName) {
        return userRepository.findUsersByNicknameContains(nickName);
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<RunRecord> getRunRecord(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getRunRecords();
        }
        return null;
    }
}
