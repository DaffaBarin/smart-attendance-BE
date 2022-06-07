package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.service.ScheduleService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import com.dipl.smartattendance.web.model.schedule.ScheduleResponse;
import com.dipl.smartattendance.web.model.schedule.UpdateScheduleRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Api
@Validated
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("Save One Schedule")
    @PostMapping(
            path = "/one",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ScheduleResponse> createOne(@RequestBody ScheduleRequest request){
        Schedule schedule = scheduleService.saveSchedule(request);
        return Response.<ScheduleResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(schedule))
                .build();
    }

    @ApiOperation("Save Many Schedule")
    @PostMapping(
            path = "/many",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<ScheduleResponse>> createMany(@RequestBody List<ScheduleRequest> request){
        List<Schedule> schedules = scheduleService.saveManySchedule(request);
        return Response.<List<ScheduleResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(toListResponse(schedules))
                .build();
    }

    @ApiOperation("Get all schedules")
    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<ScheduleResponse>> getAll(){
        List<Schedule> schedules = scheduleService.getSchedules();
        return Response.<List<ScheduleResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(toListResponse(schedules))
                .build();
    }

    @ApiOperation("Get Today schedule")
    @GetMapping(
            path = "/today",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ScheduleResponse> getToday(){
        Schedule schedules = scheduleService.getTodaySchedule();
        return Response.<ScheduleResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(schedules))
                .build();
    }

    @ApiOperation("Get is inside radius")
    @GetMapping(
            path = "/state",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Boolean> getUserState() throws IOException {
        Boolean state = scheduleService.getUserInArea();
        return Response.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .data(state)
                .build();
    }

    @ApiOperation("Delete schedule")
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Boolean> deleteSchedule(@PathVariable String id){
        scheduleService.deleteSchedule(id);
        return Response.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .data(true)
                .build();
    }

    @ApiOperation("Update Schedule")
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ScheduleResponse> updateSchedule(@PathVariable String id,
            @RequestBody UpdateScheduleRequest request){
        Schedule schedule = scheduleService.updateSchedule(id,request);
        return Response.<ScheduleResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(schedule))
                .build();
    }

    ScheduleResponse toResponse(Schedule schedule){
        ScheduleResponse scheduleResponse = ScheduleResponse.builder().build();
        BeanUtils.copyProperties(schedule,scheduleResponse);
        return scheduleResponse;
    }

    List<ScheduleResponse> toListResponse(List<Schedule> schedules){
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();
        for (Schedule schedule: schedules){
            ScheduleResponse scheduleResponse = ScheduleResponse.builder().build();
            BeanUtils.copyProperties(schedule,scheduleResponse);
            scheduleResponses.add(scheduleResponse);
        }
        return scheduleResponses;
    }
}
