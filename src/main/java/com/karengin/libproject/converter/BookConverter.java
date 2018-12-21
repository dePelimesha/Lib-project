package com.karengin.libproject.converter;

import com.karengin.libproject.dao.AuthorRepository;
import com.karengin.libproject.dbo.BookDbo;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookConverter implements DtoDboConverter<BookDto, BookDbo> {

    private final AuthorRepository authorRepository;

    @Autowired
    public BookConverter(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @Override
    public BookDto convertToDto(BookDbo dbo) {
        final BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(dbo,bookDto);
        bookDto.setAuthor(dbo.getAuthor().getName());
        return bookDto;
    }

    @Override
    public BookDbo convertToDbo(BookDto dto) {
        final BookDbo bookDbo = new BookDbo();
        bookDbo.setTitle(dto.getTitle());
        bookDbo.setDescription(dto.getDescription());
        bookDbo.setAuthor(authorRepository.getByName(dto.getAuthor()));
        return bookDbo;
    }
}
