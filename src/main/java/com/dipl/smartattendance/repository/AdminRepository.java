package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,String> {
}
