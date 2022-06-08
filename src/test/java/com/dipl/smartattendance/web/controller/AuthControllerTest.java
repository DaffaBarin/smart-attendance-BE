package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.config.jwt.JwtTokenUtil;
import com.dipl.smartattendance.entity.Admin;
import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.repository.AdminRepository;
import com.dipl.smartattendance.repository.UserRepository;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.auth.AdminAuthRequest;
import com.dipl.smartattendance.web.model.auth.AdminAuthResponse;
import com.dipl.smartattendance.web.model.auth.UserAuthRequest;
import com.dipl.smartattendance.web.model.auth.UserAuthResponse;
import io.jsonwebtoken.Jwts;
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

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    UserRepository userRepository;

    @Mock
    AdminRepository adminRepository;

    @Mock
    JwtTokenUtil jwtUtil;

    @Test
    public void userLoginSuccessTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingID")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        when(userRepository.findByNip("testingNIP")).thenReturn(user);
        when(jwtUtil.generateAccessToken(user)).thenReturn(Jwts.builder()
                .setSubject(String.format("%s,%s,%s", user.getId(), user.getNip(), user.getFullName()))
                .setIssuer("smart-attendance-backend-user")
                .setIssuedAt(new Date())
                .compact());


        UserAuthRequest userAuthRequest = UserAuthRequest.builder().nip("testingNIP").password("testingPassword").build();
        Response<UserAuthResponse> response = authController.userLogin(userAuthRequest);
        assertThat(response.getData().getAccessToken()).isNotEmpty();
    }

    @Test
    public void userLoginFailedTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id("testingID")
                .nip("testingNIP")
                .password(passwordEncoder.encode("testingPassword"))
                .fullName("testingFullName")
                .build();

        when(userRepository.findByNip("testingNIP")).thenReturn(user);


        UserAuthRequest userAuthRequest = UserAuthRequest.builder().nip("testingNIP").password("testingPasswordWrong").build();
        Response response = authController.userLogin(userAuthRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());


    }

    @Test
    public void adminLoginSuccessTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Admin admin = Admin.builder()
                .id("testingID")
                .username("usernameTesting")
                .password(passwordEncoder.encode("testingPassword"))
                .build();

        when(adminRepository.findByUsername("usernameTesting")).thenReturn(admin);
        when(jwtUtil.generateAdminAccessToken(admin)).thenReturn(Jwts.builder()
                .setSubject(String.format("%s,%s", admin.getId(), admin.getUsername()))
                .setIssuer("smart-attendance-backend-admin")
                .setIssuedAt(new Date())
                .compact());


        AdminAuthRequest adminAuthRequest = AdminAuthRequest.builder().username("usernameTesting").password("testingPassword").build();
        Response<AdminAuthResponse> response = authController.adminLogin(adminAuthRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void adminLoginFailedTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Admin admin = Admin.builder()
                .id("testingID")
                .username("usernameTesting")
                .password(passwordEncoder.encode("testingPassword"))
                .build();

        when(adminRepository.findByUsername("usernameTesting")).thenReturn(admin);


        AdminAuthRequest adminAuthRequest = AdminAuthRequest.builder().username("usernameTesting").password("testingPasswordWrong").build();
        Response response = authController.adminLogin(adminAuthRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
