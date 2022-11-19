package com.example.running.Controller;

import com.example.running.Bean.Activity;
import com.example.running.Bean.Participant;
import com.example.running.Service.ActivityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Activity")
public class ActivityController {
    @Resource(name = "ActivityService")
    ActivityService activityService;

    @RequestMapping("/GetActivity")
    public List<Activity> GetActivity(Integer pageNo) {
        return activityService.getActivity(pageNo);
    }

    @RequestMapping("/GetParticipants")
    public List<Participant> GetParticipants(Integer activityId) {
        return activityService.getParticipants(activityId);
    }
}
