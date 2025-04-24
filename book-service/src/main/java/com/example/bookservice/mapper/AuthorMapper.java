package com.example.bookservice.mapper;

import com.example.bookservice.dto.AuthorDto;
import com.example.bookservice.dto.UpdateAuthorDto;
import com.example.bookservice.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto authorToAuthorDto(Author source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    Author updateAuthorDtoToAuthor(UpdateAuthorDto source);
}
