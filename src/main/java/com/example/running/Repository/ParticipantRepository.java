package com.example.running.Repository;

import com.example.running.Bean.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository(value = "ParticipantRepository")
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from activity_participate where activity_id = ?1 and user_id = ?2")
    void deleteByActivityIdAndUserId(Integer activityId, Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update activity_participate set finish = true where activity_id = ?1 and user_id = ?2")
    void updateFinishActivity(Integer activityId, Integer userId);
}
