package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.repository.AttendanceRepository;
import com.dipl.smartattendance.repository.ScheduleRepository;
import com.dipl.smartattendance.repository.UserRepository;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service implementation for Attendance
 */
public class AttendanceServiceImpl implements AttendanceService {

    /**
     * Autowiring repository
     */
    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    /**
     * This method returns list of attendance by user id
     */
    @Override
    public List<Attendance> findByUserId(String userId) {
        return attendanceRepository.findByUserId(userId);
    }

    /**
     * This method create attendance by request
     */
    @Override
    public Attendance create(CreateAttendanceRequest request) {
        User user = userRepository.getById(request.getUserId());
        Schedule schedule = scheduleRepository.getById(request.getScheduleId());
        Attendance attendance = Attendance.builder()
                .user(user)
                .schedule(schedule)
                .build();
        return attendanceRepository.save(attendance);
    }
}
