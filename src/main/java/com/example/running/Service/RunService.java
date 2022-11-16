package com.example.running.Service;

import com.example.running.Bean.*;
import com.example.running.Repository.*;
import com.example.running.Util.RandomRoomKeyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "RunService")
public class RunService {
    @Resource(name = "RunRecordRepository")
    RunRecordRepository runRecordRepository;

    @Resource(name = "RunRoomRepository")
    RunRoomRepository runRoomRepository;

    @Resource(name = "RoomInfoRepository")
    RoomInfoRepository roomInfoRepository;

    @Resource(name = "UserRepository")
    UserRepository userRepository;

    @Resource(name = "DriftRunRepository")
    DriftRunRepository driftRunRepository;

    public RunRecord saveRecord(RunRecord runRecord) {
        runRecord.setDate(LocalDateTime.now());
        if (runRecord.getRoomId() != null) {
            // 已存在记录, 改为更新数据
            RunRecord record = runRecordRepository.findRunRecordByUserIdAndRoomId(runRecord.getUserId(), runRecord.getRoomId());
            if (record != null) {
                runRecord.setId(record.getId());
            }
            leftRoom(runRecord.getRoomId(), runRecord.getUserId());
        }
        return runRecordRepository.save(runRecord);
    }

    public RoomInfo startRoomRun(Integer userId) {
        HashSet<String> roomKeys = roomInfoRepository.findRoomKey();
        String RoomKey = RandomRoomKeyUtils.GetRandomKey();
        while (roomKeys.contains(RoomKey)) {
            RoomKey = RandomRoomKeyUtils.GetRandomKey();
        }
        // 创建房间, 初始化
        RoomInfo room = roomInfoRepository.save(new RoomInfo(null, RoomKey, 1, false));
        // 保存参加记录
        runRoomRepository.save(new RunRoom(null, room.getId(), userId));
        return room;
    }

    public Boolean joinRoom(String roomKey, Integer userId) {
        RoomInfo roomInfo = roomInfoRepository.findRoomInfoByRoomKey(roomKey);
        // 若房间存在
        if (roomInfo != null && !roomInfo.getFinish()) {
            // 房间人数 + 1
            roomInfoRepository.updateRoomPeopleNum(roomInfo.getPeopleNum() + 1, roomInfo.getId());
            // 保存参加记录
            RunRoom runRoom = runRoomRepository.findRunRoomsByRoomIdAndUserId(roomInfo.getId(), userId);
            if (runRoom == null) {
                runRoomRepository.save(new RunRoom(null, roomInfo.getId(), userId));
            }
            return true;
        }
        return false;
    }

    public Boolean leftRoom(Integer roomId, Integer userId) {
        Optional<RoomInfo> optionalRoomInfo = roomInfoRepository.findById(roomId);
        if (optionalRoomInfo.isPresent()) {
            RoomInfo roomInfo = optionalRoomInfo.get();
            if (roomInfo.getPeopleNum() == 1) {
                // 房间跑关闭
                roomInfoRepository.updateRoomFinish(roomInfo.getId());
            }
            // 更新房间跑人数
            roomInfoRepository.updateRoomPeopleNum(roomInfo.getPeopleNum() - 1, roomInfo.getId());
            // 删除用户参加房间记录
            runRoomRepository.deleteRunRoomByRoomIdAndUserId(roomId, userId);
            return true;
        }
        return false;
    }

    public List<User> getRoomUsers(Integer roomId) {
        List<Integer> userIds = runRoomRepository.findUserIdsByRoomId(roomId);
        return userRepository.findAllById(userIds);
    }

    public List<User> ranking(Integer roomId) {
        List<RunRecord> runRecordByRoomId = runRecordRepository.findRunRecordByRoomId(roomId);
        runRecordByRoomId.sort((o1, o2) -> (int) (o1.getCalorie() - o2.getCalorie()));
        List<Integer> userIds = runRecordByRoomId.stream().map(RunRecord::getUserId).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }

    public List<DriftRun> driftRoute() {
        return driftRunRepository.findAll();
    }

    public DriftRun SelectDriftRoute(Integer routeId) {
        return driftRunRepository.findById(routeId).orElse(null);
    }
}
