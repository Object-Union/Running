package com.example.running.Repository;

import com.example.running.Bean.RunRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "RunRoomRepository")
public interface RunRoomRepository extends JpaRepository<RunRoom, Integer> {
    @Query(nativeQuery = true, value = "select user_id from run_room where room_id = ?1")
    List<Integer> findUserIdsByRoomId(Integer roomId);

    RunRoom findRunRoomsByRoomIdAndUserId(Integer roomId, Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from run_room where room_id = ?1 and user_id = ?2")
    void deleteRunRoomByRoomIdAndUserId(Integer roomId, Integer userId);
}
