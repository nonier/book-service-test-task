package com.example.bookservice.rest;

import com.example.bookservice.dto.AuthorDto;
import com.example.bookservice.dto.UpdateAuthorDto;
import com.example.bookservice.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDto> findAll(Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @PostMapping
    public void create(@RequestBody @Valid UpdateAuthorDto authorDto) {
        authorService.create(authorDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateAuthorDto authorDto) {
        authorService.update(id, authorDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
