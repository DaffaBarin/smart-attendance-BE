package com.dipl.smartattendance.helper;

import com.dipl.smartattendance.entity.Schedule;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class LocationHelper {

    public String getAttendanceStatusByTimeAndLocation(LocalTime time, Map<String,Double> location, Schedule schedule) {
        Double distance = calculateDistanceInMeters(location.get("latitude"),location.get("longitude"),schedule.getLatitude(),schedule.getLongitude());
        String status;
        if (time.isBefore(LocalTime.parse("23:59"))) {
            status = "Hadir";
            if (distance < 50000){
                status = status + " Dalam Radius Kantor";
            }else {
                status = status + " Di Luar Radius Kantor";
            }
            return status;
        }

        return "Terlambat";
    }

    public double calculateDistanceInMeters(double lat1, double long1, double lat2,
                                            double long2) {
        System.out.println("lets see"+lat1+long1+lat2+long2);

        return org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
    }

    public Boolean insideRadius(Map<String,Double> location, Schedule schedule){
        Double distance = calculateDistanceInMeters(location.get("latitude"),location.get("longitude"),schedule.getLatitude(),schedule.getLongitude());
        return distance < 50000;
    }


    public Map<String,Double> getLocationByIp(HttpServletRequest servletRequest) throws IOException {
        File database = new File("src/main/resources/GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        Map<String,Double> location = new HashMap();
        if (servletRequest.getRemoteAddr() != null &&
            !servletRequest.getRemoteAddr().startsWith("0:0:") &&
                !servletRequest.getRemoteAddr().equals("127.0.0.1")){
            InetAddress ip = InetAddress.getByName(servletRequest.getRemoteAddr());
            try {
                CityResponse response = reader.city(ip);
                location.put("longitude",response.getLocation().getLongitude());
                location.put("latitude",response.getLocation().getLatitude());
            } catch (GeoIp2Exception e) {
                e.printStackTrace();
            }
        } else {
            location.put("longitude",1D);
            location.put("latitude",1D);
        }
        return location;
    }

}
