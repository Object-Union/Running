package com.example.running.Service;

import com.example.running.Bean.*;
import com.example.running.Common.TaskCommon;
import com.example.running.Repository.*;
import com.example.running.Util.RandomRoomKeyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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

    @Resource(name = "MedalService")
    MedalService medalService;

    @Transactional
    public RunRecord saveRecord(RunRecord runRecord) {
        medalService.getMedalOrNot(runRecord.getUserId(), TaskCommon.COMPLETE_1_RUN, 1);
        medalService.getMedalOrNot(runRecord.getUserId(), TaskCommon.COMPLETE_5_RUN, 1);
        medalService.getMedalOrNot(runRecord.getUserId(), TaskCommon.RUN_5_KM, runRecord.getMileage().intValue());
        medalService.getMedalOrNot(runRecord.getUserId(), TaskCommon.CONSUME_1000_CALORIE, runRecord.getCalorie().intValue());

        runRecord.setDate(LocalDateTime.now());
        // 若启用了房间跑
        if (runRecord.getRoomId() != null) {
            RunRecord record = runRecordRepository.findRunRecordByUserIdAndRoomId(runRecord.getUserId(), runRecord.getRoomId());
            if (record != null) {
                runRecord.setId(record.getId());
            }
            leftRoom(runRecord.getRoomId(), runRecord.getUserId());
        }
        // 若启用了漂移跑
        if (runRecord.getDriftRouteId() != null) {
            medalService.getMedalOrNot(runRecord.getUserId(), TaskCommon.COMPLETE_2_DRIFT_RUN, 1);
        }

        return runRecordRepository.save(runRecord);
    }

    @Transactional
    public RoomInfo startRoomRun(Integer userId, Integer routeId) {
        HashSet<String> roomKeys = roomInfoRepository.findRoomKey();
        String RoomKey = RandomRoomKeyUtils.GetRandomKey();
        while (roomKeys.contains(RoomKey)) {
            RoomKey = RandomRoomKeyUtils.GetRandomKey();
        }
        // 创建房间, 初始化
        RoomInfo room = roomInfoRepository.save(new RoomInfo(null, RoomKey, 1, false, routeId));
        // 保存参加记录
        runRoomRepository.save(new RunRoom(null, room.getId(), userId));
        return room;
    }

    @Transactional
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

    @Transactional
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

    public List<Scenery> SelectDriftRoute(Integer routeId) {
        return Objects.requireNonNull(driftRunRepository.findById(routeId).orElse(null)).getSceneries();
    }

    public List<User> driftMeet(Integer routeId, Integer userId) {
        List<Integer> userIds = runRecordRepository.findUserIdByRouteId(routeId);
        List<Integer> subscribeList = userRepository.findSubscribeIdByUserId(userId);
        ArrayList<Integer> idList = new ArrayList<>();
        for (Integer id : userIds) {
            if (!Objects.equals(id, userId) && !subscribeList.contains(id)) {
                idList.add(id);
            }
        }
        List<Integer> want = new ArrayList<>();
        Random random = new Random();
        if (idList.size() > 5) {
            while (want.size() != 5) {
                int index = random.nextInt(userIds.size());
                Integer randomId = userIds.get(index);
                if (Objects.equals(randomId, userId) || want.contains(randomId)) continue;
                want.add(randomId);
            }
        } else {
            for (Integer id : userIds) {
                if (!Objects.equals(id, userId)) {
                    want.add(id);
                }
            }
        }
        return userRepository.findAllById(want);
    }

    public void updateRoomUserPosition(Integer roomId, Integer userId, BigDecimal x, BigDecimal y) {
        runRoomRepository.updateUserPositionUserIdAndRoomId(roomId, userId, x, y);
    }

    public List<RunRoom> getRoomUserPosition(Integer roomId) {
        return runRoomRepository.findRunRoomsByRoomId(roomId);
    }
}
