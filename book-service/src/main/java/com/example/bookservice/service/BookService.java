package com.example.bookservice.service;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.dto.UpdateBookDto;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface BookService {

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> findByTitle(String title, Pageable pageable);

    void create(UpdateBookDto bookDto);

    void update(Long id, UpdateBookDto bookDto);

    void returnBook(Long id, Principal principal);

    void lendingBook(Long id, Principal principal);

    void delete(Long id);
}
