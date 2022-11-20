package com.example.running.Repository;

import com.example.running.Bean.RunRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "RunRecordRepository")
public interface RunRecordRepository extends JpaRepository<RunRecord, Integer> {
    RunRecord findRunRecordByUserIdAndRoomId(Integer userId, Integer roomId);

    List<RunRecord> findRunRecordByRoomId(Integer roomId);

    @Query(nativeQuery = true, value = "select user_id from run_record where drift_route_id = ?1")
    List<Integer> findUserIdByRouteId(Integer routeId);
}
