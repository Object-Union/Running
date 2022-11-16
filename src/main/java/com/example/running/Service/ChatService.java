package com.example.running.Service;

import com.example.running.Bean.Chat;
import com.example.running.Repository.ChatRepository;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service(value = "ChatService")
public class ChatService {
    @Resource(name = "ChatRepository")
    ChatRepository chatRepository;

    public List<Chat> getHistory(Integer senderId, Integer recipientId, Integer pageNo) {
        List<Chat> chats = chatRepository.findBySenderIdAndRecipientId(senderId, recipientId, PageRequest.of(pageNo - 1, 10, Sort.by(Sort.Direction.DESC, "id"))).getContent();
        chats.forEach(chat -> chat.setContent(EmojiParser.parseToUnicode(chat.getContent())));
        return chats;
    }

    public Chat sendMessage(Integer senderId, Integer recipientId, String content) {
        content = EmojiParser.parseToAliases(content);
        return chatRepository.save(new Chat(null, LocalDateTime.now(), senderId, recipientId, content));
    }
}
