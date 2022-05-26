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
 * Request object for attendance creation
 */
public class CreateAttendanceRequest {
    String id;
    String longitude;
    String latitude;
    LocalTime time;
    String attendanceStatus;
    String note;
    String userId;
    String scheduleId;
}