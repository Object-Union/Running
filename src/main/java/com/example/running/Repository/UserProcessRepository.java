package com.example.running.Repository;

import com.example.running.Bean.UserProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "UserProcessRepository")
public interface UserProcessRepository extends JpaRepository<UserProcess, Integer> {
    List<UserProcess> findUserProcessByUserId(Integer userId);

    UserProcess findUserProcessByUserIdAndTaskId(Integer userId, Integer taskId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update user_task set process = process + ?3 where user_id = ?1 and task_id = ?2")
    void updateUserProgress(Integer userId, Integer taskId, Integer delta);
}
