package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for table name attendances
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,String> {
    List<Attendance> findByUserId(String userId);
}
