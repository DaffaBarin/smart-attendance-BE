package com.dipl.smartattendance.helper;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class AttendanceHelper {

    public String getAttendanceStatusByTime(LocalTime time) {
        if (time.isBefore(LocalTime.parse("09:05"))) {
            return "Hadir";
        }
        return "Terlambat";
    }

    //    public Map getLocationByIp(HttpServletRequest request){
//
//    }
    public Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

        Map<String, String> result = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }

        return result;
    }
}
