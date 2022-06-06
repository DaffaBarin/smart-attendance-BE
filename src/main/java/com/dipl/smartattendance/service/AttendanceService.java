package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Service interface for Attendance
 */
public interface AttendanceService {
    List<Attendance> findByUserId(String userId);
    Attendance create(CreateAttendanceRequest request) throws IOException;
}
