package com.example.running.Repository;

import com.example.running.Bean.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository(value = "ChatRepository")
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query(nativeQuery = true, value = "select * from chat where (sender_id = ?1 and recipient_id = ?2) or (sender_id = ?2 and recipient_id = ?1)")
    Page<Chat> findBySenderIdAndRecipientId(Integer senderId, Integer recipientId, Pageable pageable);
}
