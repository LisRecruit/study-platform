package com.example.study_platform.auth.user;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

//security purpose. User shouldn't know about SpringSecurity
@Getter
@ToString(exclude = "password")
public class CustomUserDetails implements UserDetails, Serializable {
//    private final User user;
    @Serial
    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String role;
    private final Long schoolId;

    public CustomUserDetails(Long id,
                             String username,
                             String password,
                             String email,
                             String role,
                             Long schoolId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.schoolId = schoolId;
        }
//    public CustomUserDetails(User user) {
//        this.user = user;
//    }


//    public CustomUserDetails(User user, List<GrantedAuthority> authorities) {
//        this.user = user;
//    }

    @Override
    public List<GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password; // Если у вас есть пароль в объекте User
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно настроить по необходимости
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Можно настроить по необходимости
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Можно настроить по необходимости
    }

    @Override
    public boolean isEnabled() {
        return true; // Можно настроить по необходимости
    }

    public Long getUserId() {
        return id;
    }


}
