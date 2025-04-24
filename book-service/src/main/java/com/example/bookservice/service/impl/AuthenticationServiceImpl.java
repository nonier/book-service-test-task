package com.example.bookservice.service.impl;

import com.example.bookservice.dto.JwtDto;
import com.example.bookservice.dto.SignRequest;
import com.example.bookservice.entity.User;
import com.example.bookservice.service.AuthenticationService;
import com.example.bookservice.service.JwtService;
import com.example.bookservice.service.UserService;
import com.example.bookservice.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtDto signUp(SignRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.USER);
        User user = userService.createUser(newUser);
        String token = jwtService.generateToken(user);
        return new JwtDto(token);
    }

    @Override
    public JwtDto signIn(SignRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));
        User user = userService.loadByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return new JwtDto(token);
    }
}
