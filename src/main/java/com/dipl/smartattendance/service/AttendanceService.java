package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import com.dipl.smartattendance.web.model.attendance.UpdateAttendanceRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Service interface for Attendance
 */
public interface AttendanceService {

    List<Attendance> findByUserId(String userId);

    Attendance create(CreateAttendanceRequest request) throws IOException;

    List<Attendance> getAttendances();

    Attendance updateAttendance(UpdateAttendanceRequest request);

    Attendance findById(String id);

    void deleteAttendance(String id);
}
