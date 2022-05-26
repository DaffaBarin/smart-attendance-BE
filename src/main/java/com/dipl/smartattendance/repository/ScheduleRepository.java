package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for table name schedules
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,String> {
}
