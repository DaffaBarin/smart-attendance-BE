package com.dipl.smartattendance.web.model.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttendanceRequest {
    String id;

    Double longitude;

    Double latitude;

    LocalTime time;

    String attendanceStatus;

    String note;
}
