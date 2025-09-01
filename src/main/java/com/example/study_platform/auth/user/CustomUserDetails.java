package com.example.study_platform.auth.user;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

//security purpose. User shouldn't know about SpringSecurity
@Getter
@ToString
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    public CustomUserDetails(User user, List<GrantedAuthority> authorities) {
        this.user = user;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {

        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Если у вас есть пароль в объекте User
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
        return user.getId();
    }
}
