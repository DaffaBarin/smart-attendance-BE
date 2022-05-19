package com.dipl.smartattendance.repository;

import com.dipl.smartattendance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findOneByNip(String nip);
}
