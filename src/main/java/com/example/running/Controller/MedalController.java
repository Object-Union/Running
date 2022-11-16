package com.example.running.Controller;

import com.example.running.Bean.Medal;
import com.example.running.Service.MedalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Medal")
public class MedalController {
    @Resource(name = "MedalService")
    MedalService medalService;

    @RequestMapping("/Info")
    public List<Medal> Info() {
        return medalService.info();
    }

    @RequestMapping("/Personal")
    public List<Integer> Personal(Integer userId) {
        return medalService.personal(userId);
    }

    @RequestMapping("/GetMedal")
    public Integer GetMedal(Integer userId, Integer medalId) {
        return medalService.getMedal(userId, medalId);
    }
}
