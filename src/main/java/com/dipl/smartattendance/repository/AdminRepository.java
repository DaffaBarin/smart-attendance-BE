package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for table name admins
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    Admin findByUsername(String username);
}
