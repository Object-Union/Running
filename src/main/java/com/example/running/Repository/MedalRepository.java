package com.example.running.Repository;

import com.example.running.Bean.Medal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "MedalRepository")
public interface MedalRepository extends JpaRepository<Medal, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into user_medal values (null, ?1, ?2)")
    Integer insertGetMedalRecord(Integer userId, Integer medalId);

    @Query(nativeQuery = true, value = "select medal_id from run.user_medal where user_id = ?1")
    List<Integer> findUserGetMedalIdList(Integer userId);
}
