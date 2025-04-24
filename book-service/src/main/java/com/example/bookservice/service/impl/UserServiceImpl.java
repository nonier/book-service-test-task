package com.example.bookservice.service.impl;

import com.example.bookservice.entity.User;
import com.example.bookservice.repository.UserRepository;
import com.example.bookservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(null);
    }

    @Override
    public User getUserFromPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElse(null);
    }

    @Override
    public User loadByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
