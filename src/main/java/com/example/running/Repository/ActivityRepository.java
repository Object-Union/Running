package com.example.running.Repository;

import com.example.running.Bean.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository(value = "ActivityRepository")
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findActivityById(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update activity set join_num = ?2 where id = ?1")
    void updateActivityJoinNumById(Integer activityId, Integer joinNum);
}
