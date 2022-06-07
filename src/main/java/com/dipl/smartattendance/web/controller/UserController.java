package com.dipl.smartattendance.web.controller;

import com.dipl.smartattendance.config.jwt.JwtTokenFilter;
import com.dipl.smartattendance.entity.User;
import com.dipl.smartattendance.helper.JwtHelper;
import com.dipl.smartattendance.service.AttendanceService;
import com.dipl.smartattendance.service.UserService;
import com.dipl.smartattendance.web.model.Response;
import com.dipl.smartattendance.web.model.user.UpdateUserRequest;
import com.dipl.smartattendance.web.model.user.UserRequest;
import com.dipl.smartattendance.web.model.user.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Api
@Validated
@RestController
@RequestMapping("/api/user")
/**
 * Controller for User
 */
@Slf4j
public class UserController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    JwtHelper jwtHelper;

    @ApiOperation("Save User")
    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<UserResponse> saveOne(@RequestBody UserRequest request){
        User user = userService.saveUser(request);
        return Response.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(user))
                .build();
    }

    @ApiOperation("Find user by NIP")
    @GetMapping(
            path = "/nip/{nip}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<UserResponse> findByNip(@PathVariable String nip){
        log.info(jwtHelper.getToken(request));
        User user = userService.getUser(nip);
        return Response.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(user))
                .build();
    }

    @ApiOperation("Get all user")
    @GetMapping(
            path = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<UserResponse>> getAll(){
        List<User> users = userService.getUsers();
        return Response.<List<UserResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(users))
                .build();
    }
    @ApiOperation("Update user by ID")
    @PutMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<UserResponse> updateUserById(@RequestBody UpdateUserRequest request){
        User user = userService.updateUser(request);
        return Response.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(user))
                .build();
    }

    @ApiOperation("Delete user by ID")
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Boolean> deleteUserById(@PathVariable String id){
        userService.deleteUser(id);
        return Response.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .data(true)
                .build();
    }
    /**
     * User entity to UserResponse transformation
     */
    private UserResponse toResponse(User user){
        UserResponse userResponse = UserResponse.builder().build();
        BeanUtils.copyProperties(user,userResponse);
        return userResponse;
    }
    /**
     * List of User entity to List ofUserResponse transformation
     */
    private List<UserResponse> toResponse(List<User> users){
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user: users){
            UserResponse userResponse = UserResponse.builder().build();
            BeanUtils.copyProperties(user,userResponse);
            userResponses.add(userResponse);
        }
        return userResponses;
    }
}
