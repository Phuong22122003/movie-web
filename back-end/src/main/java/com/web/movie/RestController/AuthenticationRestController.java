package com.web.movie.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.web.movie.Dto.LoginRequest;
import com.web.movie.Dto.SigupRequest;
import com.web.movie.Dto.response.UserDto;
import com.web.movie.Service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/authenticate")
@Tag(name = "Authentication")
public class AuthenticationRestController {

    @Autowired private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(userService.login(loginRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> sigup(@RequestBody SigupRequest sigupRequest){
        return ResponseEntity.ok().body(userService.addUser(sigupRequest));
    }
}
