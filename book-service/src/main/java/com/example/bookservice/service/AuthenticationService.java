package com.example.bookservice.service;

import com.example.bookservice.dto.JwtDto;
import com.example.bookservice.dto.SignRequest;

public interface AuthenticationService {

    JwtDto signUp(SignRequest request);

    JwtDto signIn(SignRequest request);
}
