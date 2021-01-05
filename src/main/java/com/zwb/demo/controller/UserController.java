package com.zwb.demo.controller;

import com.zwb.demo.bean.SchedulerTasks;
import com.zwb.demo.config.quartz.QuartzSchedulerConfig;
import com.zwb.demo.repository.SchedulerTasksRepository;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @param * @param null
 * @author zhouw
 * @description: TODO
 * @return
 * @throws
 * @date 2021/1/2 14:18
 */
@RestController
public class UserController {

    @Autowired
    QuartzSchedulerConfig quartzSchedulerConfig;
    @Autowired
    SchedulerTasksRepository schedulerTasksRepository;

    @GetMapping("/getUserInfo")
    public Map userInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map map = new HashMap<>();
        map.put("userName", userDetails.getUsername());
        map.put("userPassword", userDetails.getPassword());
        map.put("userRoles", userDetails.getAuthorities().toString());
        return map;
    }

    @PostMapping("/addSchedulerJob")
    public String addSchedulerJob(@RequestBody SchedulerTasks schedulerTasks) {
        try {
            schedulerTasksRepository.save(schedulerTasks);
            Scheduler scheduler = quartzSchedulerConfig.addJob(schedulerTasks);
//            quartzSchedulerConfig.startJobSingle(scheduler);
            return "添加定时任务成功";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "添加定时任务失败";
    }

    /**
     * 开始的特定的定时任务
     *
     * @param schedulerTasks
     * @return
     * @throws SchedulerException
     */
    @PostMapping("/startSchedulerJob")
    public String startSchedulerJob(@RequestBody SchedulerTasks schedulerTasks) throws SchedulerException {
        //SchedulerTasks schedulerTasks1 = schedulerTasksRepository.findByName(schedulerTasks.getName());
        Scheduler scheduler = quartzSchedulerConfig.addJob(schedulerTasks);
        schedulerTasks.setStatus("1");
        schedulerTasksRepository.save(schedulerTasks);
        scheduler.start();
        return "开始定时任务 " + schedulerTasks.getName() + " 成功!";
    }

    @PostMapping("/stopSchedulerJob")
    public String stopSchedulerJob(@RequestBody SchedulerTasks schedulerTasks) throws SchedulerException {
//        SchedulerTasks schedulerTasks = schedulerTasksRepository.findByName(schedulerTasks.getName());
        // 更新定时任务的状态为0
        schedulerTasks.setStatus("0");
        schedulerTasksRepository.save(schedulerTasks);
        String groupName = schedulerTasks.getGroupName();
        quartzSchedulerConfig.deleteJob(schedulerTasks.getName(), groupName);
        return "停止定时任务 " + schedulerTasks.getName() + " 成功!";
    }
}
