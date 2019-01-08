package com.karengin.libproject;

import com.karengin.libproject.Entity.*;
import com.karengin.libproject.dto.*;

import java.util.Arrays;

public class MockData {

    private static final long ID = 1L;
    private static final String USER_LOGIN = "testUser";
    private static final String USER_PASSWORD = "pass123";
    private static final String AUTHOR_NAME = "testAuthor";
    private static final String BOOK_TITLE = "testTitle";
    private static final String BOOK_DESCRIPTION = "description";
    private static final String COMMENT = "comment";
    private static final String USER_ROLE = "USER";
    private static final String GENRE = "genre";

    public static UsersEntity usersEntity() {
        final UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(ID);
        usersEntity.setLogin(USER_LOGIN);
        usersEntity.setPassword(USER_PASSWORD);
        usersEntity.setUserRole(MockData.usersRoleEntity());
        return usersEntity;
    }

    public static UsersDto usersDto() {
        final UsersDto usersDto = new UsersDto();
        usersDto.setId(ID);
        usersDto.setLogin(USER_LOGIN);
        usersDto.setPassword(USER_PASSWORD);
        return usersDto;
    }

    public static AuthorEntity authorEntity() {
        final AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(ID);
        authorEntity.setName(AUTHOR_NAME);
        return authorEntity;
    }

    public static AuthorDto authorDto() {
        final AuthorDto authorDto = new AuthorDto();
        authorDto.setId(ID);
        authorDto.setName(AUTHOR_NAME);
        return authorDto;
    }

    public static BookEntity bookEntity() {
        final BookEntity bookEntity = new BookEntity();
        bookEntity.setId(ID);
        bookEntity.setTitle(BOOK_TITLE);
        bookEntity.setAuthor(MockData.authorEntity());
        bookEntity.setDescription(BOOK_DESCRIPTION);
        bookEntity.setGenresList(Arrays.asList(MockData.genreEntity(),MockData.genreEntity()));
        return bookEntity;
    }

    public static BookDto bookDto() {
        final BookDto bookDto = new BookDto();
        bookDto.setId(ID);
        bookDto.setTitle(BOOK_TITLE);
        bookDto.setAuthor(AUTHOR_NAME);
        bookDto.setDescription(BOOK_DESCRIPTION);
        bookDto.setGenres(Arrays.asList(GENRE,GENRE));
        return bookDto;
    }

    public static CommentsEntity commentsEntity() {
        final CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setId(ID);
        commentsEntity.setComment(COMMENT);
        commentsEntity.setUser(MockData.usersEntity());
        commentsEntity.setBook(MockData.bookEntity());
        return commentsEntity;
    }

    public static CommentsDto commentsDto() {
        final CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(ID);
        commentsDto.setComment(COMMENT);
        commentsDto.setUserName(USER_LOGIN);
        return commentsDto;
    }

    public static UsersRoleEntity usersRoleEntity() {
        final UsersRoleEntity usersRoleEntity = new UsersRoleEntity();
        usersRoleEntity.setId(ID);
        usersRoleEntity.setRole(USER_ROLE);
        return usersRoleEntity;
    }

    public static GenreEntity genreEntity() {
        final GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(ID);
        genreEntity.setGenre(GENRE);
        return genreEntity;
    }

    public static GenreDto genreDto() {
        final GenreDto genreDto = new GenreDto();
        genreDto.setId(ID);
        genreDto.setGenre(GENRE);
        return genreDto;
    }
}
