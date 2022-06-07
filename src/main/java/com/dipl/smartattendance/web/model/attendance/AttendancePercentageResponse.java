package com.dipl.smartattendance.web.model.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendancePercentageResponse {

    Integer present;

    Integer latePresent;

    Integer notPresent;
}
