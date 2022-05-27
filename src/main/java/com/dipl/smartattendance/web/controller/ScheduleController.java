package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.service.ScheduleService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.schedule.ScheduleRequest;
import com.dipl.smartattendance.web.model.schedule.ScheduleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@Validated
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("Save Schedule")
    @PostMapping(
            path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ScheduleResponse> saveOne(@RequestBody ScheduleRequest request){
        Schedule schedule = scheduleService.saveSchedule(request);
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
}
