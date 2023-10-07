package com.example.project.api.controller;

import com.example.project.config.JwtTokenUtil;
import com.example.project.dto.user.UserCreateDTO;
import com.example.project.dto.user.UserEditDTO;
import com.example.project.dto.user.UserLoginDTO;
import com.example.project.exception.RoleException;
import com.example.project.exception.UserException;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.api.service.implementations.RoleService;
import com.example.project.api.service.interfaces.UserServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserServiceAPI userService;
    private final RoleService roleService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserServiceAPI userService, RoleService roleService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginDTO user) {
        final User foundUser = userService.findByUsername(user.getName());

        if(!user.getPassword().equals(foundUser.getPassword()))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        final String token = jwtTokenUtil.generateToken(foundUser);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                ).build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Validated @RequestBody UserCreateDTO userCreateDTO) {
        return new ResponseEntity<>(userService.save(userCreateDTO), HttpStatus.OK);
    }

    @PostMapping("/addRole")
    public ResponseEntity<Role> addRole(@Validated @RequestBody Role role) {
        return new ResponseEntity<>(roleService.save(role), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@Validated @RequestBody UserEditDTO userEditDTO) {
        return new ResponseEntity<>(userService.update(userEditDTO), HttpStatus.OK);
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<String> handleRoleException(RoleException roleException){
        log.error("User controller exception " + roleException.getMessage());
        return new ResponseEntity<>(roleException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException userException){
        log.error("User controller exception " + userException.getMessage());
        return new ResponseEntity<>(userException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
