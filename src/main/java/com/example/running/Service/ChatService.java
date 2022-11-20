package com.example.running.Service;

import com.example.running.Bean.Chat;
import com.example.running.Repository.ChatRepository;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service(value = "ChatService")
public class ChatService {
    @Resource(name = "ChatRepository")
    ChatRepository chatRepository;

    @Transactional
    public List<Chat> getHistory(Integer senderId, Integer recipientId, Integer pageNo) {
        List<Chat> chats = chatRepository.findBySenderIdAndRecipientId(senderId, recipientId, PageRequest.of(pageNo - 1, 5, Sort.by(Sort.Direction.DESC, "id"))).getContent();
        chatRepository.deleteUnCheckChatBySenderIdAndRecipientId(senderId, recipientId);
        chats.forEach(chat -> chat.setContent(EmojiParser.parseToUnicode(chat.getContent())));
        return chats;
    }

    public Chat sendMessage(Chat chat) {
        chat.setDate(LocalDateTime.now());
        chat.setContent(EmojiParser.parseToAliases(chat.getContent()));
        return chatRepository.save(chat);
    }

    public List<List<Chat>> getListHistory(Integer userId, List<Integer> others) {
        ArrayList<List<Chat>> chatList = new ArrayList<>();
        for (Integer other : others) {
            List<Chat> chats = getHistory(other, userId, 1);
            chatList.add(chats);
        }
        return chatList;
    }

    public void storeUncheckChat(Chat chat) {
        Integer uncheckNum = chatRepository.searchUncheckChat(chat.getSenderId(), chat.getRecipientId());
        if (uncheckNum == null) {
            chatRepository.insertUncheckChat(chat.getSenderId(), chat.getRecipientId());
        } else {
            chatRepository.addNumOfUnCheckChat(chat.getSenderId(), chat.getRecipientId(), uncheckNum + 1);
        }
    }

    public List<List<Chat>> getUnCheckChat(Integer userId) {
        List<Integer> senderIds = chatRepository.findUncheckChatSenderIdByRecipientId(userId);
        return getListHistory(userId, senderIds);
    }
}
