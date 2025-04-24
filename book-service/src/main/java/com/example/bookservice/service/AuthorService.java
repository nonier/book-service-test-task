package com.example.bookservice.service;

import com.example.bookservice.dto.AuthorDto;
import com.example.bookservice.dto.UpdateAuthorDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll(Pageable pageable);

    void create(UpdateAuthorDto authorDto);

    void update(Long id, UpdateAuthorDto authorDto);

    void delete(Long id);
}
