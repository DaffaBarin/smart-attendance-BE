package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,String> {
}
