package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.repository.ScheduleRepository;
import com.dipl.smartattendance.service.ScheduleService;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import com.dipl.smartattendance.web.model.schedule.UpdateScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule saveSchedule(ScheduleRequest request) {
        Schedule schedule = Schedule.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .date(request.getDate())
                .build();
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> saveManySchedule(List<ScheduleRequest> requestList) {
        List<Schedule> schedules = toScheduleList(requestList);
        return scheduleRepository.saveAll(schedules);
    }

    @Override
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public void deleteSchedule(String id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public Schedule updateSchedule(String id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.getById(id);
        BeanUtils.copyProperties(request,schedule);
        return scheduleRepository.save(schedule);
    }


    List<Schedule> toScheduleList(List<ScheduleRequest> requestList){
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleRequest request: requestList){
            Schedule schedule = Schedule.builder().build();
            BeanUtils.copyProperties(request,schedule);
            schedules.add(schedule);
        }
        return schedules;
    }

}
