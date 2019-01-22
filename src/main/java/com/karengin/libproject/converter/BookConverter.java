package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookConverter extends AbstractConverter<BookDto, BookEntity> {
    private final AuthorRepository authorRepository;
    private final GenreConverter genreConverter;

    @Override
    public BookDto convertToDto(final BookEntity bookEntity) {
        final BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(bookEntity,bookDto);
        bookDto.setAuthor(bookEntity.getAuthor().getName());
        bookDto.setGenres(genreConverter.convertToDto(bookEntity.getGenresList()));
        return bookDto;
    }

    @Override
    public BookEntity convertToEntity(final BookDto dto) {
        final BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(dto, bookEntity);
        bookEntity.setAuthor(authorRepository.getByName(dto.getAuthor()));
        bookEntity.setGenresList(genreConverter.convertToEntity(dto.getGenres()));
        return bookEntity;
    }
}
