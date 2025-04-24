package com.example.bookservice.service;

import com.example.bookservice.entity.User;

import java.security.Principal;

public interface UserService {

    User getCurrentUser();

    User getUserFromPrincipal(Principal principal);

    User loadByUsername(String username);

    User createUser(User user);
}
