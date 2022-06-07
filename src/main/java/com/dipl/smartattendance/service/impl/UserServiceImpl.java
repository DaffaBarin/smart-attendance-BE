package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.repository.UserRepository;
import com.dipl.smartattendance.service.UserService;
import com.dipl.smartattendance.web.model.user.UpdateUserRequest;
import com.dipl.smartattendance.web.model.user.UserRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service implementation for User
 */
public class UserServiceImpl implements UserService {

    /**
     * Autowiring repository
     */
    private final UserRepository userRepository;

    /**
     * This method create user by request
     */
    @Override
    public User saveUser(UserRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .nip(request.getNip())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepository.save(user);
    }

    /**
     * This method returns all list of users
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * This method finds user by nip
     */
    @Override
    public User getUser(String nip) {
        return userRepository.findByNip(nip);
    }

    @Override
    public User updateUser(UpdateUserRequest request) {
        User user = userRepository.getById(request.getId());
        BeanUtils.copyProperties(request,user);
        System.out.println(request.getNip());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
