package com.example.bookservice.mapper;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.dto.UpdateBookDto;
import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorRepository authorRepository;

    @Mapping(target = "authorName", source = "source.author.name")
    @Mapping(target = "isLending", expression = "java(source.getUser() != null)")
    public abstract BookDto bookToBookDto(Book source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "author", expression = "java(authorRepository.getReferenceById(source.getAuthorId()))")
    public abstract Book updateBookDtoToBook(UpdateBookDto source);
}
