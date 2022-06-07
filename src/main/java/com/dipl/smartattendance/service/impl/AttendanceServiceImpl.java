package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.helper.LocationHelper;
import com.dipl.smartattendance.repository.AttendanceRepository;
import com.dipl.smartattendance.repository.ScheduleRepository;
import com.dipl.smartattendance.repository.UserRepository;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.web.model.attendance.AttendancePercentageResponse;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import com.dipl.smartattendance.web.model.attendance.UpdateAttendanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
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

    private final LocationHelper locationHelper;

    @Autowired
    private HttpServletRequest servletRequest;

    /**
     * This method returns list of attendance by user id
     */
    @Override
    public List<Attendance> findByUserId(String userId) {
        return attendanceRepository.findAllByUserId(userId);
    }

    /**
     * This method create attendance by request
     */
    @Override
    public Attendance create(CreateAttendanceRequest request) throws IOException {
        User user = userRepository.getById(request.getUserId());
        Schedule schedule = scheduleRepository.getById(request.getScheduleId());
        if (attendanceRepository.existsByUserAndSchedule(user,schedule)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Attendance attendance = Attendance.builder().build();
        BeanUtils.copyProperties(request,attendance);
        Map<String,Double> location = locationHelper.getLocationByIp(servletRequest);
        attendance.setLatitude(location.get("longitude"));
        attendance.setLongitude(location.get("latitude"));
        attendance.setTime(LocalTime.now(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(7))));
        attendance.setAttendanceStatus(locationHelper.getAttendanceStatusByTimeAndLocation(attendance.getTime(),location,schedule));
        attendance.setUser(user);
        attendance.setSchedule(schedule);
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance updateAttendance(UpdateAttendanceRequest request) {
        Attendance attendance = attendanceRepository.getById(request.getId());
        BeanUtils.copyProperties(request,attendance);
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance findById(String id) {
        return attendanceRepository.getById(id);
    }

    @Override
    public void deleteAttendance(String id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public AttendancePercentageResponse getPercentage(String userId) {
        Calendar cal = Calendar.getInstance();
        List<Schedule> schedules = scheduleRepository.getScheduleByDateBetween(LocalDate.now().withDayOfMonth(1),LocalDate.now().withDayOfMonth(cal.getActualMaximum(Calendar.DATE)));
        List<Attendance> presentAttendances = attendanceRepository.findAllByUserIdAndAttendanceStatusContaining(userId,"Hadir");
        List<Attendance> lateAttendances = attendanceRepository.findAllByUserIdAndAttendanceStatusContaining(userId,"Terlambat");
        return AttendancePercentageResponse.builder()
                .latePresent(lateAttendances.size())
                .present(presentAttendances.size())
                .notPresent(schedules.size() - presentAttendances.size() + lateAttendances.size())
                .build();
    }
}
