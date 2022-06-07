package com.dipl.smartattendance.web.model.attendance;

import com.dipl.smartattendance.entity.Schedule;
import com.dipl.smartattendance.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Response object for attendance
 */
public class AttendanceResponse {
    String id;
    Double longitude;
    Double latitude;
    LocalTime time;
    String attendanceStatus;
    String note;
    String userNip;
    String userId;
    String scheduleId;
}
