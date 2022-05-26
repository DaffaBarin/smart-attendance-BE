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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api
@Validated
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminRepository adminRepo;

    @ApiOperation("User Login")
    @PostMapping(
            path = "/user/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<UserAuthResponse> userLogin(@RequestBody UserAuthRequest request){
//        Authentication authentication = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getNip(), request.getPassword())
//        );
        try{
//            User user = (User) authentication.getPrincipal();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user = userRepo.findByNip(request.getNip());
            if (!passwordEncoder.matches(request.getPassword(),user.getPassword())) throw new BadCredentialsException("Username or Password Wrong");
            String accessToken = jwtUtil.generateAccessToken(user);
            UserAuthResponse response = UserAuthResponse.builder().nip(user.getNip()).accessToken(accessToken).build();
            return Response.<UserAuthResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(response)
                    .build();
        } catch (Exception ex){
            return Response.<UserAuthResponse>builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }

    @ApiOperation("Admin Login")
    @PostMapping(
            path = "/admin/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<AdminAuthResponse> adminLogin(@RequestBody AdminAuthRequest request){
//        Authentication authentication = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(), request.getPassword())
//        );
        try{
//
//            Admin admin = (Admin) authentication.getPrincipal();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Admin admin = adminRepo.findByUsername(request.getUsername());
            if (!passwordEncoder.matches(request.getPassword(),admin.getPassword())) throw new BadCredentialsException("Username or Password Wrong");
            String accessToken = jwtUtil.generateAdminAccessToken(admin);
            AdminAuthResponse response = AdminAuthResponse.builder().username(admin.getUsername()).accessToken(accessToken).build();
            return Response.<AdminAuthResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(response)
                    .build();
        } catch (Exception ex){
            return Response.<AdminAuthResponse>builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }
}
