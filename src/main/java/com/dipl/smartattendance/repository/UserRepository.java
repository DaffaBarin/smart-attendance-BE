package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for table name users
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByNip(String nip);
}
