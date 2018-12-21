package com.karengin.libproject.service;

import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.dao.AuthorRepository;
import com.karengin.libproject.dto.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;

    @Autowired
    public AuthorService(final AuthorRepository authorRepository, final AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    public List<AuthorDto> getAuthorsList() {
        return authorRepository.findAll().stream().map(authorConverter::convertToDto).collect(Collectors.toList());
    }

    public void addAuthor(AuthorDto authorDto) {
        authorRepository.save(authorConverter.convertToDbo(authorDto));
    }

    public AuthorDto getAuthor(String name) {
        return authorConverter.convertToDto(authorRepository.getByName(name));
    }
}
