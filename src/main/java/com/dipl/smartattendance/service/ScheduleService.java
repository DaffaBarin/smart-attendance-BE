package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;

public interface ScheduleService {
    Schedule saveSchedule(ScheduleRequest request);
}
