package com.example.bookservice.service.impl;

import com.example.bookservice.service.BookService;
import com.example.bookservice.service.LendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LendingServiceImpl implements LendingService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final BookService bookService;

    @Override
    public CompletableFuture<Void> lendingBook(Long bookId, Principal principal) {
        return CompletableFuture.runAsync(new DelegatingSecurityContextRunnable(() -> bookService.lendingBook(bookId, principal)), executorService)
                .exceptionally(ex -> {
                    log.error("Error while lending book: {} cause: {}", bookId, ex.getMessage());
                    switch (ex.getCause()) {
                        case ObjectOptimisticLockingFailureException e ->
                                throw new ResponseStatusException(HttpStatus.CONFLICT,
                                        "Book with id: %s already lending".formatted(bookId));
                        case ResponseStatusException e -> throw e;
                        default -> throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
                    }
                });
    }
}
