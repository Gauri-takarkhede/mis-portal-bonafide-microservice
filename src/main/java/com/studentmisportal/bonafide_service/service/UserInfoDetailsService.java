package com.studentmisportal.bonafide_service.service;

import com.studentmisportal.bonafide_service.dto.UserDto;
import com.studentmisportal.bonafide_service.feign.StudentServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String mis) throws UsernameNotFoundException {
        // In a microservice, we trust the JWT signature — no DB or remote call needed.
        // Return a minimal UserDetails built from the token claims alone.
        return new User(
                mis,
                "",
                List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))
        );
    }
}