package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.helper.AttendanceHelper;
import com.dipl.smartattendance.repository.AttendanceRepository;
import com.dipl.smartattendance.repository.ScheduleRepository;
import com.dipl.smartattendance.repository.UserRepository;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
/**
 * Service implementation for Attendance
 */
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    /**
     * Autowiring repository
     */
    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    private final AttendanceHelper attendanceHelper;

    @Autowired
    private HttpServletRequest servletRequest;

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
    public Attendance create(CreateAttendanceRequest request) throws IOException {
        User user = userRepository.getById(request.getUserId());
        Schedule schedule = scheduleRepository.getById(request.getScheduleId());
        Attendance attendance = Attendance.builder().build();
        BeanUtils.copyProperties(request,attendance);
        Map<String,Double> location = attendanceHelper.getLocationByIp(servletRequest);
        attendance.setLatitude(location.get("longitude"));
        attendance.setLongitude(location.get("latitude"));
        attendance.setTime(LocalTime.now(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(7))));
        attendance.setAttendanceStatus(attendanceHelper.getAttendanceStatusByTimeAndLocation(attendance.getTime(),location,schedule));
        attendance.setUser(user);
        attendance.setSchedule(schedule);
        return attendanceRepository.save(attendance);
    }
}
