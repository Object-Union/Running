package com.example.running.Controller;

import com.example.running.Bean.*;
import com.example.running.Service.RunService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/Run")
public class RunController {
    @Resource(name = "RunService")
    RunService runService;

    @RequestMapping("/SaveRecord")
    public RunRecord SaveRecord(RunRecord runRecord) {
        return runService.saveRecord(runRecord);
    }

    @RequestMapping("/StartRoomRun")
    public RoomInfo StartRoomRun(Integer userId, Integer routeId) {
        return runService.startRoomRun(userId, routeId);
    }

    @RequestMapping("/JoinRoom")
    public Boolean JoinRoom(String roomKey, Integer userId) {
        return runService.joinRoom(roomKey, userId);
    }

    @RequestMapping("/LeftRoom")
    public Boolean LeftRoom(Integer roomId, Integer userId) {
        return runService.leftRoom(roomId, userId);
    }

    @RequestMapping("/GetRoomUsers")
    public List<User> GetRoomUsers(Integer roomId) {
        return runService.getRoomUsers(roomId);
    }

    @RequestMapping("/Ranking")
    public List<User> Ranking(Integer roomId) {
        return runService.ranking(roomId);
    }

    @RequestMapping("/DriftRoute")
    public List<DriftRun> DriftRoute() {
        return runService.driftRoute();
    }

    @RequestMapping("/SelectDriftRoute")
    public List<Scenery> SelectDriftRoute(Integer routeId) {
        return runService.SelectDriftRoute(routeId);
    }

    @RequestMapping("/DriftMeet")
    public List<User> DriftMeet(Integer routeId, Integer userId) {
        return runService.driftMeet(routeId, userId);
    }

    @RequestMapping("/UpdateRoomUserPosition")
    public void UpdateRoomUserPosition(Integer roomId, Integer userId, BigDecimal x, BigDecimal y) {
        runService.updateRoomUserPosition(roomId, userId, x, y);
    }

    @RequestMapping("/GetRoomUserPosition")
    public List<RunRoom> GetRoomUserPosition(Integer roomId) {
        return runService.getRoomUserPosition(roomId);
    }
}
