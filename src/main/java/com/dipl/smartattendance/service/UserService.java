package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.User;

import java.util.List;


public interface UserService {
    User saveUser(User user);
    List<User> getUsers();
    User getUser(String nip);
}
