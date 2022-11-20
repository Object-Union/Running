package com.example.running.Service;

import com.example.running.Bean.Activity;
import com.example.running.Bean.Participant;
import com.example.running.Repository.ActivityRepository;
import com.example.running.Repository.ParticipantRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service(value = "ActivityService")
public class ActivityService {
    @Resource(name = "ActivityRepository")
    ActivityRepository activityRepository;

    @Resource(name = "ParticipantRepository")
    ParticipantRepository participantRepository;

    public List<Activity> getActivity(Integer pageNo) {
        return activityRepository.findAll(PageRequest.of(pageNo - 1, 5)).getContent();
    }

    public List<Participant> getParticipants(Integer activityId) {
        return Objects.requireNonNull(activityRepository.findById(activityId).orElse(null)).getParticipants();
    }

    @Transactional
    public Boolean join(Integer userId, Integer activityId) {
        participantRepository.save(new Participant(null, activityId, userId, false));
        Activity activity = activityRepository.findActivityById(activityId);
        activityRepository.updateActivityJoinNumById(activityId, activity.getJoinNum() + 1);
        return true;
    }

    @Transactional
    public Boolean unJoin(Integer userId, Integer activityId) {
        participantRepository.deleteByActivityIdAndUserId(activityId, userId);
        Activity activity = activityRepository.findActivityById(activityId);
        activityRepository.updateActivityJoinNumById(activityId, activity.getJoinNum() - 1);
        return true;
    }

    public void finish(Integer userId, Integer activityId) {
        participantRepository.updateFinishActivity(activityId, userId);
    }
}
