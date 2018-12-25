package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.dto.BookDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookConverter implements DtoEntityConverter<BookDto, BookEntity> {

    private final AuthorRepository authorRepository;

    @Override
    public BookDto convertToDto(final BookEntity bookEntity) {
        final BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(bookEntity,bookDto);
        bookDto.setAuthor(bookEntity.getAuthor().getName());
        return bookDto;
    }

    @Override
    public BookEntity convertToEntity(final BookDto dto) {
        final BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(dto.getTitle());
        bookEntity.setDescription(dto.getDescription());
        bookEntity.setAuthor(authorRepository.getByName(dto.getAuthor()));
        return bookEntity;
    }
}
