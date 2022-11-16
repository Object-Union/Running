package com.example.running.Repository;

import com.example.running.Bean.RunRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "RunRecordRepository")
public interface RunRecordRepository extends JpaRepository<RunRecord, Integer> {
    RunRecord findRunRecordByUserIdAndRoomId(Integer userId, Integer roomId);

    List<RunRecord> findRunRecordByRoomId(Integer roomId);
}
