package com.example.bookservice.service.impl;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.dto.UpdateBookDto;
import com.example.bookservice.entity.Book;
import com.example.bookservice.entity.User;
import com.example.bookservice.mapper.BookMapper;
import com.example.bookservice.repository.AuthorRepository;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookService;
import com.example.bookservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final UserService userService;

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAllJoinFetchAuthor(pageable)
                .stream()
                .map(bookMapper::bookToBookDto)
                .toList();
    }

    @Override
    public List<BookDto> findByTitle(String title, Pageable pageable) {
        return bookRepository.findAllByTitleJoinFetchAuthor(title, pageable)
                .stream()
                .map(bookMapper::bookToBookDto)
                .toList();
    }

    @Override
    public void create(UpdateBookDto bookDto) {
        bookRepository.save(bookMapper.updateBookDtoToBook(bookDto));
    }

    @Override
    public void update(Long id, UpdateBookDto bookDto) {
        bookRepository.findById(id)
                .ifPresentOrElse(
                        b -> {
                            b.setTitle(bookDto.getTitle());
                            b.setAuthor(authorRepository.getReferenceById(bookDto.getAuthorId()));
                            bookRepository.save(b);
                        }, () -> {
                            log.error("Book with id: {} not found", id);
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("Book with id: %s not found", id));
                        }
                );
    }

    @Override
    public void returnBook(Long id, Principal principal) {
        Book book = bookRepository.findByIdJoinFetchUser(id)
                .orElseThrow(() -> {
                    log.error("Invalid call from user: {}. Book with id: {} not found", principal.getName(), id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Book with id: %s not found".formatted(id));
                });
        if (Objects.isNull(book.getUser())) {
            log.error("Invalid call from user: {}. Book with id: {} already returned", principal.getName(), id);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with id: %s already returned".formatted(id));
        }
        if (!Objects.equals(book.getUser().getUsername(), principal.getName())) {
            log.error("Invalid call from user: {}. Book with id: {} held by another user: {}",
                    principal.getName(), id, book.getUser().getUsername());
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Book with id: %s held by another user".formatted(id));
        }
        book.setUser(null);
        bookRepository.save(book);
    }

    @Override
    public void lendingBook(Long id, Principal principal) throws ObjectOptimisticLockingFailureException {
        bookRepository.findByIdAndUserIsNullWithOptimisticLock(id)
                .ifPresentOrElse(book -> {
                    try {
                        // Имитация работы
                        Thread.sleep(Duration.ofSeconds(5));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    User user = userService.getUserFromPrincipal(principal);
                    book.setUser(user);
                    user.getBooks().add(book);
                    bookRepository.save(book);
                }, () -> {
                    log.error("The book with id: {} is already lending", id);
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Book with id: %s is already lending".formatted(id));
                });
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
