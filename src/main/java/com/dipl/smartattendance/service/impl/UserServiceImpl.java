package com.dipl.smartattendance.service.impl;

import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.repository.UserRepository;
import com.dipl.smartattendance.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String nip) {
        return userRepository.findOneByNip(nip);
    }
}
