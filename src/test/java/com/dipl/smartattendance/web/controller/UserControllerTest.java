package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.service.UserService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.user.UpdateUserRequest;
import com.dipl.smartattendance.web.model.user.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    public void saveOneSuccessTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingID")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        UserRequest userRequest = UserRequest.builder()
                .fullName("testingFullName")
                .nip("testingNIP")
                .password("testingPassword")
                .build();

        when(userService.saveUser(userRequest)).thenReturn(user);

        Response response = userController.saveOne(userRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void findByNipSuccessTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingID")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        when(userService.getUser("testingNIP")).thenReturn(user);

        Response response = userController.findByNip("testingNIP");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getAllSuccessTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingID")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();
        User user2 = User.builder()
                .id("testingID2")
                .nip("testingNIP2")
                .password(passwordEncoder.encode("testingPassword2"))
                .fullName("testingFullName2")
                .build();
        List<User> users = Arrays.asList(user,user2);

        when(userService.getUsers()).thenReturn(users);

        Response response = userController.getAll();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void updateUserByIdSuccessTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingID")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        UpdateUserRequest userRequest = UpdateUserRequest.builder()
                .id("testingID")
                .nip("testingNIP")
                .fullName("testingFullName")
                .build();

        when(userService.updateUser(userRequest)).thenReturn(user);

        Response response = userController.updateUserById(userRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteUserByIdSuccessTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Response response = userController.deleteUserById("testingID");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}
