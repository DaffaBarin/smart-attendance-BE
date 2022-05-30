package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.attendance.AttendanceResponse;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Api
@Validated
@RestController
@RequestMapping("/api/attendance")
/**
 * Controller for Attendance
 */
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @ApiOperation("Create new attendance")
    @PostMapping(
            path="/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<AttendanceResponse> create(@RequestBody CreateAttendanceRequest request){
        Attendance attendance = attendanceService.create(request);
        return Response.<AttendanceResponse>builder()
                .data(toResponse(attendance))
                .build();
    }

    @ApiOperation("Find Attendance by User Id")
    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<AttendanceResponse>> findByUserId(@PathVariable String id){
        List<Attendance> attendances = attendanceService.findByUserId(id);
        return Response.<List<AttendanceResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(attendances))
                .build();
    }
    /**
     * Attendance entity to AttendanceResponse transformation
     */
    private AttendanceResponse toResponse(Attendance attendance) {
        AttendanceResponse attendanceResponse = AttendanceResponse.builder().build();
        BeanUtils.copyProperties(attendance,attendanceResponse);
        return attendanceResponse;
    }

    /**
     * List of Attendance entity to List of AttendanceResponse transformation
     */
    private List<AttendanceResponse> toResponse(List<Attendance> attendances) {
        List<AttendanceResponse> attendanceResponses = Collections.emptyList();
        for (Attendance attendance: attendances){
            AttendanceResponse attendanceResponse = AttendanceResponse.builder().build();
            BeanUtils.copyProperties(attendance,attendanceResponse);
            attendanceResponses.add(attendanceResponse);
        }
        return attendanceResponses;
    }

    private Response.Pagination toResponse(Page<?> page){
        return Response.Pagination.builder()
                .page(page.getNumber())
                .size((long) page.getSize())
                .totalItems(page.getTotalElements())
                .build();
    }

}
