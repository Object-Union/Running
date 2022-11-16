package com.example.running.Controller;

import com.example.running.Bean.Comment;
import com.example.running.Bean.Moment;
import com.example.running.Service.MomentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Moment")
public class MomentController {
    @Resource(name = "MomentService")
    MomentService momentService;

    @RequestMapping("/Publish")
    public Moment Publish(@RequestParam("userId") Integer userId, @RequestParam("title") String title, @RequestParam("content") String content, @RequestBody(required = false) List<MultipartFile> pictures) throws IOException {
        return momentService.publish(new Moment(title, content, userId, 0, LocalDateTime.now()), pictures);
    }

    @RequestMapping("/Like")
    public Integer Like(Integer userId, Integer momentId) {
        return momentService.like(userId, momentId);
    }

    @RequestMapping("/LikeList")
    public List<Integer> LikeList(Integer userId) {
        return momentService.likeList(userId);
    }

    @RequestMapping("/Detail")
    public Moment Detail(Integer momentId) {
        return momentService.detail(momentId);
    }

    @RequestMapping("/AddComment")
    public Comment AddComment(Integer momentId, Integer userId, String comment) {
        return momentService.addComment(momentId, userId, comment);
    }

    @RequestMapping("/GetComments")
    public List<Comment> GetComments(Integer momentId, Integer pageNo) {
        return momentService.getComments(momentId, pageNo);
    }

    @RequestMapping("/GetMoments")
    public List<Moment> GetMoments(Integer pageNo) {
        return momentService.getMoments(pageNo);
    }

    @RequestMapping("/GetUserMoments")
    public List<Moment> GetUserMoments(Integer userId, Integer pageNo) {
        return momentService.getUserMoments(userId, pageNo);
    }
}
