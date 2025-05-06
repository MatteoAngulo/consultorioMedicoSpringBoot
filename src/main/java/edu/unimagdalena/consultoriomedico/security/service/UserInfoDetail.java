package edu.unimagdalena.consultoriomedico.security.service;

import edu.unimagdalena.consultoriomedico.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfoDetail implements UserDetails{
    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final Set<GrantedAuthority> authorities;
    public UserInfoDetail(UserEntity user){
        this.id = user.getIdUser();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

