package com.web.movie.Config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
    private String id;
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities){
        this.authorities = authorities;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
}
