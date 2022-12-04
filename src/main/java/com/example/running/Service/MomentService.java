package com.example.running.Service;

import com.example.running.Bean.Comment;
import com.example.running.Bean.Moment;
import com.example.running.Bean.User;
import com.example.running.Common.Upload;
import com.example.running.Repository.CommentRepository;
import com.example.running.Repository.MomentRepository;
import com.example.running.Repository.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "MomentService")
public class MomentService {
    @Resource(name = "MomentRepository")
    MomentRepository momentRepository;

    @Resource(name = "UserRepository")
    UserRepository userRepository;

    @Resource(name = "CommentRepository")
    CommentRepository commentRepository;

    @Transactional
    public Moment publish(Moment moment, List<MultipartFile> pictures) throws IOException {
        Moment save = momentRepository.save(moment);
        if (pictures != null) {
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < pictures.size(); i++) {
                String uri = save.getId() + "_" + i + ".png";
                urls.add(Upload.BASE_URL + "moment/" + uri);
//                pictures.get(i).transferTo(new File(Upload.MOMENT_DIR + uri));
                pictures.get(i).transferTo(new File(Upload.UPLOAD_MOMENT_DIR + uri));
            }
            save.setPictures(urls);
            return momentRepository.save(save);
        }
        return save;
    }

    @Transactional
    public Integer like(Integer userId, Integer momentId) {
        Optional<Moment> optionalMoment = momentRepository.findById(momentId);
        if (optionalMoment.isPresent()) {
            Moment moment = optionalMoment.get();
            Integer likeNum = moment.getLikeNum();
            Integer likeId = userRepository.findLikedIdByUserIdAndMomentId(userId, momentId);
            if (likeId != null) {
                likeNum--;
                userRepository.deleteALikeRecord(likeId);
            } else {
                likeNum++;
                userRepository.insertALikeRecord(userId, momentId);
            }
            moment.setLikeNum(likeNum);
            momentRepository.save(moment);
            return likeNum;
        } else {
            return null;
        }
    }

    public List<Integer> likeList(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getLikedArticle();
        }
        return null;
    }

    public Moment detail(Integer momentId) {
        Optional<Moment> optionalMoment = momentRepository.findById(momentId);
        if (optionalMoment.isPresent()) {
            Moment moment = optionalMoment.get();
            moment.setTitle(EmojiParser.parseToUnicode(moment.getTitle()));
            moment.setContent(EmojiParser.parseToUnicode(moment.getContent()));
            return moment;
        }
        return null;
    }

    public Comment addComment(Integer momentId, Integer userId, String comment) {
        comment = EmojiParser.parseToAliases(comment);
        return commentRepository.save(new Comment(momentId, userId, comment, LocalDateTime.now()));
    }

    public List<Comment> getComments(Integer momentId, Integer pageNo) {
        List<Comment> comments = commentRepository.findCommentsByMomentId(momentId, PageRequest.of(pageNo - 1, 10)).getContent();
        comments.forEach(comment -> comment.setComment(EmojiParser.parseToUnicode(comment.getComment())));
        return comments;
    }

    public List<Moment> getMoments(Integer pageNo) {
        List<Moment> moments = momentRepository.findAll(PageRequest.of(pageNo - 1, 5)).getContent();
        moments.forEach(moment -> moment.setTitle(EmojiParser.parseToUnicode(moment.getTitle())));
        return moments;
    }

    public List<Moment> getUserMoments(Integer userId, Integer pageNo) {
        List<Moment> moments = momentRepository.findByUserId(userId, PageRequest.of(pageNo - 1, 5)).getContent();
        moments.forEach(moment -> moment.setTitle(EmojiParser.parseToUnicode(moment.getTitle())));
        return moments;
    }

    public List<Moment> getSubscribeMoments(Integer userId, Integer pageNo) {
        List<Integer> subscribeList = userRepository.findSubscribeIdByUserId(userId);
        return momentRepository.findByUserIdIn(subscribeList, PageRequest.of(pageNo - 1, 5)).getContent();
    }
}
