package com.example.bookservice.service.impl;

import com.example.bookservice.dto.AuthorDto;
import com.example.bookservice.dto.UpdateAuthorDto;
import com.example.bookservice.entity.Author;
import com.example.bookservice.mapper.AuthorMapper;
import com.example.bookservice.repository.AuthorRepository;
import com.example.bookservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .stream()
                .map(authorMapper::authorToAuthorDto)
                .toList();
    }

    @Override
    public void create(UpdateAuthorDto authorDto) {
        authorRepository.save(authorMapper.updateAuthorDtoToAuthor(authorDto));
    }

    @Override
    public void update(Long id, UpdateAuthorDto authorDto) {
        authorRepository.findById(id)
                .ifPresentOrElse(
                        a -> {
                            Author author = authorMapper.updateAuthorDtoToAuthor(authorDto);
                            author.setId(id);
                            authorRepository.save(author);
                        }, () -> {
                            log.error("Author with id: {} not found", id);
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Author with id: " + id + " not found");
                        }
                );
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
