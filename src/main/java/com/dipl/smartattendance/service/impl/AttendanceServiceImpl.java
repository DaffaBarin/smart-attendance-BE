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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

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
    public Attendance create(CreateAttendanceRequest request) {
        User user = userRepository.getById(request.getUserId());
        Schedule schedule = scheduleRepository.getById(request.getScheduleId());
        Attendance attendance = Attendance.builder().build();
        BeanUtils.copyProperties(request,attendance);
        String remoteAddr = servletRequest.getHeader("X-FORWARDED-FOR");
        log.info("ASIAP"+remoteAddr);
        log.info("ASIAAP"+servletRequest.getRemoteAddr());
        log.info("lets see"+attendanceHelper.getRequestHeadersInMap(servletRequest));
        attendance.setLatitude("1");
        attendance.setLongitude("1");
        attendance.setUser(user);
        attendance.setSchedule(schedule);
        attendance.setTime(LocalTime.now(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(7))));
        attendance.setAttendanceStatus(attendanceHelper.getAttendanceStatusByTime(attendance.getTime()));
        return attendanceRepository.save(attendance);
    }
}
