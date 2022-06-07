package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.Attendance;
import com.dipl.smartattendance.helper.JwtHelper;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.attendance.AttendanceResponse;
import com.dipl.smartattendance.web.model.attendance.CreateAttendanceRequest;
import com.dipl.smartattendance.web.model.attendance.UpdateAttendanceRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api
@Validated
@RestController
@RequestMapping("/api/attendance")
/**
 * Controller for Attendance
 */
@Slf4j
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    JwtHelper jwtHelper;

    @ApiOperation("Create new attendance")
    @PostMapping(
            path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<AttendanceResponse> create(@RequestBody CreateAttendanceRequest request,
                                               @RequestHeader(value = "Authorization") String token) throws IOException {
        Attendance attendance = attendanceService.create(request);
        return Response.<AttendanceResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(attendance))
                .build();
    }

    @ApiOperation("Find Attendance by Id")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<AttendanceResponse> findById(@PathVariable String id) {
        Attendance attendance = attendanceService.findById(id);
        return Response.<AttendanceResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(attendance))
                .build();
    }

    @ApiOperation("Find Attendances by User Id")
    @GetMapping(
            path = "/user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<AttendanceResponse>> findByUserId(@PathVariable String userId) {
        List<Attendance> attendances = attendanceService.findByUserId(userId);
        return Response.<List<AttendanceResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(toListResponse(attendances))
                .build();
    }

    @ApiOperation("Get all attendances")
    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<AttendanceResponse>> getAll() {
        List<Attendance> attendances = attendanceService.getAttendances();
        return Response.<List<AttendanceResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(toListResponse(attendances))
                .build();
    }

    @ApiOperation("Update attendance by Id")
    @PutMapping(
            path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<AttendanceResponse> updateAttendance(@RequestBody UpdateAttendanceRequest request) {
        Attendance attendance = attendanceService.updateAttendance(request);
        return Response.<AttendanceResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(attendance))
                .build();
    }

    @ApiOperation("Delete attendance by Id")
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Boolean> DeleteAttendance(@PathVariable String id) {
        attendanceService.deleteAttendance(id);
        return Response.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .data(true)
                .build();
    }

    /**
     * Attendance entity to AttendanceResponse transformation
     */
    private AttendanceResponse toResponse(Attendance attendance) {
        AttendanceResponse attendanceResponse = AttendanceResponse.builder().build();
        BeanUtils.copyProperties(attendance, attendanceResponse);
        return attendanceResponse;
    }

    /**
     * List of Attendance entity to List of AttendanceResponse transformation
     */
    private List<AttendanceResponse> toListResponse(List<Attendance> attendances) {
        List<AttendanceResponse> attendanceResponses = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceResponse attendanceResponse = AttendanceResponse.builder().build();
            BeanUtils.copyProperties(attendance, attendanceResponse);
            attendanceResponses.add(attendanceResponse);
        }
        return attendanceResponses;
    }

    private Response.Pagination toResponse(Page<?> page) {
        return Response.Pagination.builder()
                .page(page.getNumber())
                .size((long) page.getSize())
                .totalItems(page.getTotalElements())
                .build();
    }

}
