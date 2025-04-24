package com.example.bookservice.rest;

import com.example.bookservice.dto.JwtDto;
import com.example.bookservice.dto.SignRequest;
import com.example.bookservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtDto signUp(@RequestBody @Valid SignRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtDto signIn(@RequestBody @Valid SignRequest request) {
        return authenticationService.signIn(request);
    }
}
