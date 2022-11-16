package com.example.running.Repository;

import com.example.running.Bean.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository(value = "RoomInfoRepository")
public interface RoomInfoRepository extends JpaRepository<RoomInfo, Integer> {
    @Query(nativeQuery = true, value = "select room_key from room_info")
    HashSet<String> findRoomKey();

    RoomInfo findRoomInfoByRoomKey(String roomKey);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update room_info set people_num = ?1 where id = ?2")
    Integer updateRoomPeopleNum(Integer peopleNum, Integer roomId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update room_info set finish = true where id = ?1")
    Integer updateRoomFinish(Integer roomId);
}
