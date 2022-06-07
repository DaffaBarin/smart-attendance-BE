package com.dipl.smartattendance.service;

import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.web.model.user.UpdateUserRequest;
import com.dipl.smartattendance.web.model.user.UserRequest;

import java.util.List;

/**
 * Service interface for User
 */
public interface UserService {
    User saveUser(UserRequest request);
    List<User> getUsers();
    User getUser(String nip);
    User updateUser(UpdateUserRequest request);
    void deleteUser(String id);
}
