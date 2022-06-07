package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.Schedule;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

/**
 * Repository for table name schedules
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,String> {

    Schedule getScheduleByDateEquals(LocalDate date);

    List<Schedule> getScheduleByDateBetween(LocalDate date1, LocalDate date2);
}
