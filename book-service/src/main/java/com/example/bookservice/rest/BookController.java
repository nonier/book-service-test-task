package com.example.bookservice.rest;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.dto.UpdateBookDto;
import com.example.bookservice.service.BookService;
import com.example.bookservice.service.LendingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final LendingService lendingService;

    @GetMapping
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/title/{title}")
    public List<BookDto> findByTitle(@PathVariable String title, Pageable pageable) {
        return bookService.findByTitle(title, pageable);
    }

    @PostMapping
    public void create(@RequestBody @Valid UpdateBookDto updateBookDto) {
        bookService.create(updateBookDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateBookDto updateBookDto) {
        bookService.update(id, updateBookDto);
    }

    @DeleteMapping(("/{id}"))
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping("/return/{id}")
    public void returnBook(@PathVariable Long id, Principal principal) {
        bookService.returnBook(id, principal);
    }

    @PutMapping("/lending/{id}")
    public ResponseEntity<Void> lending(@PathVariable Long id, Principal principal) {
        lendingService.lendingBook(id, principal).join();
        return ResponseEntity.ok().build();
    }
}
