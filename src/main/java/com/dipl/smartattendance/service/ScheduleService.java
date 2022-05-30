package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import com.dipl.smartattendance.web.model.schedule.UpdateScheduleRequest;

import java.util.List;

public interface ScheduleService {
    Schedule saveSchedule(ScheduleRequest request);

    List<Schedule> saveManySchedule(List<ScheduleRequest> requestList);

    List<Schedule> getSchedules();

    void deleteSchedule(String id);

    Schedule updateSchedule(String id, UpdateScheduleRequest request);
}
