package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.service.ScheduleService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import com.dipl.smartattendance.web.model.schedule.ScheduleResponse;
import com.dipl.smartattendance.web.model.schedule.UpdateScheduleRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ScheduleControllerTest {

    @InjectMocks
    ScheduleController scheduleController;

    @Mock
    ScheduleService scheduleService;

    @Test
    public void createOneTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Schedule schedule = Schedule.builder()
                .id("testingId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .date(LocalDate.now())
                .latitude(1D)
                .longitude(1D)
                .build();

        when(scheduleService.saveSchedule(scheduleRequest)).thenReturn(schedule);

        Response response = scheduleController.createOne(scheduleRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void createManyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Schedule schedule1 = Schedule.builder()
                .id("testingId1")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Schedule schedule2 = Schedule.builder()
                .id("testingId2")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        List<Schedule> schedules = Arrays.asList(schedule1,schedule2);

        ScheduleRequest scheduleRequest1 = ScheduleRequest.builder()
                .date(LocalDate.now())
                .latitude(1D)
                .longitude(1D)
                .build();

        ScheduleRequest scheduleRequest2 = ScheduleRequest.builder()
                .date(LocalDate.now())
                .latitude(1D)
                .longitude(1D)
                .build();

        List<ScheduleRequest> scheduleRequest = Arrays.asList(scheduleRequest1,scheduleRequest2);

        when(scheduleService.saveManySchedule(scheduleRequest)).thenReturn(schedules);

        Response response = scheduleController.createMany(scheduleRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getAllTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Schedule schedule1 = Schedule.builder()
                .id("testingId1")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        Schedule schedule2 = Schedule.builder()
                .id("testingId2")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        List<Schedule> schedules = Arrays.asList(schedule1,schedule2);

        when(scheduleService.getSchedules()).thenReturn(schedules);

        Response response = scheduleController.getAll();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getTodayTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Schedule schedule = Schedule.builder()
                .id("testingId1")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        when(scheduleService.getTodaySchedule()).thenReturn(schedule);

        Response<ScheduleResponse> response = scheduleController.getToday();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData().getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    public void getUserStateTest() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(scheduleService.getUserInArea()).thenReturn(true);

        Response response = scheduleController.getUserState();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteScheduleTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Response response = scheduleController.deleteSchedule("testingId");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void updateScheduleTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Schedule schedule = Schedule.builder()
                .id("testingId")
                .latitude(1D)
                .longitude(1D)
                .date(LocalDate.now())
                .build();

        UpdateScheduleRequest scheduleRequest = UpdateScheduleRequest.builder()
                .date(LocalDate.now())
                .latitude(1D)
                .longitude(1D)
                .build();

        when(scheduleService.updateSchedule("testingId",scheduleRequest)).thenReturn(schedule);

        Response response = scheduleController.updateSchedule("testingId",scheduleRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}