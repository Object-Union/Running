package com.example.running.Repository;

import com.example.running.Bean.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "ChatRepository")
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query(nativeQuery = true, value = "select * from chat where (sender_id = ?1 and recipient_id = ?2) or (sender_id = ?2 and recipient_id = ?1)")
    Page<Chat> findBySenderIdAndRecipientId(Integer senderId, Integer recipientId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into chat_uncheck values (null, ?1, ?2, 1)")
    void insertUncheckChat(Integer senderId, Integer recipientId);

    @Query(nativeQuery = true, value = "select uncheck_num from chat_uncheck where sender_id = ?1 and recipient_id = ?2")
    Integer searchUncheckChat(Integer senderId, Integer recipientId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update chat_uncheck set uncheck_num = ?3 where sender_id = ?1 and recipient_id = ?2")
    void addNumOfUnCheckChat(Integer senderId, Integer recipientId, Integer uncheckNum);

    @Query(nativeQuery = true, value = "select sender_id from chat_uncheck where recipient_id = ?1")
    List<Integer> findUncheckChatSenderIdByRecipientId(Integer recipientId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from chat_uncheck where sender_id = ?1 and recipient_id = ?2")
    void deleteUnCheckChatBySenderIdAndRecipientId(Integer senderId, Integer recipientId);
}
