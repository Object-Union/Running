package com.example.running.Controller;

import com.example.running.Bean.Chat;
import com.example.running.Service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Chat")
public class ChatController {
    @Resource(name = "ChatService")
    ChatService chatService;

    @RequestMapping("/GetHistory")
    public List<Chat> GetHistory(Integer senderId, Integer recipientId, Integer pageNo) {
        return chatService.getHistory(senderId, recipientId, pageNo);
    }

    @RequestMapping("/SendMessage")
    public Chat SendMessage(Integer senderId, Integer recipientId, String content) {
        return chatService.sendMessage(senderId, recipientId, content);
    }
}
