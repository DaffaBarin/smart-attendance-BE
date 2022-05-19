package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AttendanceService {
    List<Attendance> findByUserId(String userId);
    Attendance create(CreateAttendanceRequest request);
}
