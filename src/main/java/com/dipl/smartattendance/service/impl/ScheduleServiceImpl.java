package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.helper.LocationHelper;
import com.dipl.smartattendance.repository.ScheduleRepository;
import com.dipl.smartattendance.service.ScheduleService;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import com.dipl.smartattendance.web.model.schedule.UpdateScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final LocationHelper locationHelper;

    @Autowired
    private HttpServletRequest servletRequest;

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

    @Override
    public Schedule getTodaySchedule() {
        return scheduleRepository.getScheduleByDateEquals(LocalDate.now(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(7))));
    }

    @Override
    public Boolean getUserInArea() throws IOException {
        Schedule schedule = scheduleRepository.getScheduleByDateEquals(LocalDate.now(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(7))));
        Map<String,Double> location = locationHelper.getLocationByIp(servletRequest);
        return locationHelper.insideRadius(location,schedule);
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
