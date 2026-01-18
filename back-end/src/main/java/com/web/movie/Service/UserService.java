package com.web.movie.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.movie.Config.JwtTokenProvider;
import com.web.movie.CustomException.BadRequestException;
import com.web.movie.CustomException.NotFoundException;
import com.web.movie.Dto.LoginRequest;
import com.web.movie.Dto.SigupRequest;
import com.web.movie.Dto.response.UserDto;
import com.web.movie.Entity.User;
import com.web.movie.Repository.UserRepository;
import com.web.movie.mapper.UserMapper;


@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserMapper userMapper;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Cacheable(value = "users", key = "#username")
    public UserDto findUserByUsername(String username){
        User user =  userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("Username not found"));
        user.setPassword(null);
        
        return userMapper.toUserDto(user);
    }
    @CachePut(value = "users", key = "#sigupRequest.username")
    public UserDto addUser(SigupRequest sigupRequest){
        User user = userMapper.toUser(sigupRequest);
        user.setPassword(passwordEncoder.encode(sigupRequest.getPassword()));
        user.setRole("ROLE_USER");
        User savedUser = null;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Username or email are in used");
        }
        return userMapper.toUserDto(savedUser);
    }

    public Map<String,String> login(LoginRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch (Exception ex){
            throw new BadRequestException("Username or password are incorrect");
        }
        String jwt = jwtTokenProvider.generateToken(request.getUsername());
        Map<String,String> res = new HashMap<>();
        res.put("jwt", jwt);
        return res;
    }
}
