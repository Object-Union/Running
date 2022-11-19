package com.example.running.Service;

import com.example.running.Bean.Activity;
import com.example.running.Bean.Participant;
import com.example.running.Repository.ActivityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service(value = "ActivityService")
public class ActivityService {
    @Resource(name = "ActivityRepository")
    ActivityRepository activityRepository;

    public List<Activity> getActivity(Integer pageNo) {
        return activityRepository.findAll(PageRequest.of(pageNo - 1, 5)).getContent();
    }

    public List<Participant> getParticipants(Integer activityId) {
        return Objects.requireNonNull(activityRepository.findById(activityId).orElse(null)).getParticipants();
    }
}
