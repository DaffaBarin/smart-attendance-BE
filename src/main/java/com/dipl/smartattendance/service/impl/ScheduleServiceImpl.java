package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.repository.ScheduleRepository;
import com.dipl.smartattendance.service.ScheduleService;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
