package com.example.running.Repository;

import com.example.running.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "UserRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update `user_info` set avatar = ?1 where id = ?2")
    void updateAvatar(String avatar, Integer userId);

    User findUserByUsernameAndPassword(String username, String password);

    @Query(nativeQuery = true, value = "select id from user_like where user_id = ?1 and moment_id = ?2")
    Integer findLikedIdByUserIdAndMomentId(Integer userId, Integer momentId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into user_like values (null, ?1, ?2)")
    void insertALikeRecord(Integer userId, Integer momentId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from user_like where id = ?1")
    void deleteALikeRecord(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into friend values (null, ?1, ?2)")
    Integer insertASubscribeRecord(Integer userId1, Integer userId2);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from friend where user_id = ?1 and friend_id = ?2")
    Integer deleteASubscribeRecord(Integer userId1, Integer userId2);

    List<User> findUsersByNicknameContains(String nickname);

    @Query(nativeQuery = true, value = "select user_id from friend where friend_id = ?1")
    List<Integer> findFansIdByUserId(Integer userId);
}
