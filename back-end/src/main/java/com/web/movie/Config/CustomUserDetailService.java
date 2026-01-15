package com.web.movie.Config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.movie.CustomException.NotFoundException;
import com.web.movie.Repository.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService{
    @Autowired private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        com.web.movie.Entity.User user = userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User not found"));
        if(user==null) throw new UsernameNotFoundException("Not found");
        else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().trim().toUpperCase()));
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}
