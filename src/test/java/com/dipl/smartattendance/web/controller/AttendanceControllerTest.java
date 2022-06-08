package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.attendance.AttendancePercentageResponse;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import com.dipl.smartattendance.web.model.attendance.UpdateAttendanceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AttendanceControllerTest {

    @InjectMocks
    AttendanceController attendanceController;

    @Mock
    AttendanceService attendanceService;

    @Test
    public void createAttendanceTest() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingUserId")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        Schedule schedule = Schedule.builder()
                .id("testingScheduleId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Attendance attendance = Attendance.builder()
                .id("testingAttendanceId")
                .note("testingNote")
                .attendanceStatus("Hadir")
                .latitude(1D)
                .longitude(1D)
                .time(LocalTime.now())
                .schedule(schedule)
                .user(user)
                .build();

        CreateAttendanceRequest createAttendanceRequest = CreateAttendanceRequest.builder()
                .note("testingNote")
                .scheduleId("testingScheduleId")
                .userId("testingUserId")
                .build();

        when(attendanceService.create(createAttendanceRequest)).thenReturn(attendance);

        Response response = attendanceController.create(createAttendanceRequest,"token");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void findByIdTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingUserId")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        Schedule schedule = Schedule.builder()
                .id("testingScheduleId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Attendance attendance = Attendance.builder()
                .id("testingAttendanceId")
                .note("testingNote")
                .attendanceStatus("Hadir")
                .latitude(1D)
                .longitude(1D)
                .time(LocalTime.now())
                .schedule(schedule)
                .user(user)
                .build();

        when(attendanceService.findById("testingAttendanceId")).thenReturn(attendance);

        Response response = attendanceController.findById("testingAttendanceId");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void findByUserIdTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingUserId")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        Schedule schedule = Schedule.builder()
                .id("testingScheduleId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Attendance attendance = Attendance.builder()
                .id("testingAttendanceId")
                .note("testingNote")
                .attendanceStatus("Hadir")
                .latitude(1D)
                .longitude(1D)
                .time(LocalTime.now())
                .schedule(schedule)
                .user(user)
                .build();

        List<Attendance> attendances = Arrays.asList(attendance);

        when(attendanceService.findByUserId("testingUserId")).thenReturn(attendances);

        Response response = attendanceController.findByUserId("testingUserId");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getAllTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingUserId")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        Schedule schedule = Schedule.builder()
                .id("testingScheduleId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Attendance attendance = Attendance.builder()
                .id("testingAttendanceId")
                .note("testingNote")
                .attendanceStatus("Hadir")
                .latitude(1D)
                .longitude(1D)
                .time(LocalTime.now())
                .schedule(schedule)
                .user(user)
                .build();

        List<Attendance> attendances = Arrays.asList(attendance);

        when(attendanceService.getAttendances()).thenReturn(attendances);

        Response response = attendanceController.getAll();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void updateAttendanceTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingUserId")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        Schedule schedule = Schedule.builder()
                .id("testingScheduleId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Attendance attendance = Attendance.builder()
                .id("testingAttendanceId")
                .note("testingNote")
                .attendanceStatus("Hadir")
                .latitude(1D)
                .longitude(1D)
                .time(LocalTime.now())
                .schedule(schedule)
                .user(user)
                .build();

        UpdateAttendanceRequest updateAttendanceRequest = UpdateAttendanceRequest.builder()
                .note("testingNote")
                .id("testingAttendanceId")
                .note("testingNote")
                .attendanceStatus("Hadir")
                .latitude(1D)
                .longitude(1D)
                .time(LocalTime.now())
                .build();

        when(attendanceService.updateAttendance(updateAttendanceRequest)).thenReturn(attendance);

        Response response = attendanceController.updateAttendance(updateAttendanceRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteAttendanceTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Response response = attendanceController.deleteAttendance("testingId");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getPercentageTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AttendancePercentageResponse attendancePercentageResponse = AttendancePercentageResponse.builder()
                .notPresent(1)
                .present(1)
                .latePresent(1)
                .build();

        when(attendanceService.getPercentage("testingId")).thenReturn(attendancePercentageResponse);

        Response response = attendanceController.getPercentage("testingId");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}