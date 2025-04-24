package com.example.bookservice.service;


import java.security.Principal;
import java.util.concurrent.CompletableFuture;

public interface LendingService {

    CompletableFuture<Void> lendingBook(Long bookId, Principal principal);
}
