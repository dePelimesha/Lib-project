package com.karengin.libproject.UI.form;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.UI.customField.GenresField;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.GenreRepository;
import com.karengin.libproject.service.AbstractService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.GenreService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringComponent
@ViewScope
public class BookEditForm extends AbstractEditForm<BookDto, BookService> {

    @Autowired
    private AbstractService<GenreDto, GenreEntity, GenreRepository> genreService;

    @Autowired
    private AbstractService<AuthorDto, AuthorEntity, AuthorRepository> authorService;

    public BookEditForm(final BookDto bookDto, final BookService bookService) {
        super(bookDto, bookService);
    }

    @Override
    protected void bindEntityFields() {
        bindTitleField();
        bindDescriptionField();
        bindAuthorField();
        bindGenresField();
    }

    private void bindTitleField() {
        final TextField titleField = new TextField("Title");
        getBinder().forField(titleField)
                .bind(BookDto::getTitle, BookDto::setTitle);
        addComponent(titleField);
    }

    private void bindDescriptionField() {
        final TextField descriptionField = new TextField("Description");

        getBinder().forField(descriptionField)
                .bind(BookDto::getDescription, BookDto::setDescription);
        addComponent(descriptionField);
    }

    private void bindAuthorField() {
        final ComboBox<String> authorsField = new ComboBox<>("Author");
        List<String> authors = new ArrayList<>();
        authorService.findAll().getBody()
                .forEach(authorDto -> authors.add(authorDto.getName()));
        ListDataProvider<String> authorDataProvider = DataProvider.ofCollection(authors);

        authorsField.setDataProvider(authorDataProvider);

        getBinder().forField(authorsField)
                .withValidator(Objects::nonNull, "Author required")
                .bind(BookDto::getAuthor, BookDto::setAuthor);
        addComponent(authorsField);
    }

    private void bindGenresField() {
        List<String> genres = new ArrayList<>();
        genreService.findAll().getBody()
                .forEach(genreDto -> genres.add(genreDto.getGenre()));

        final GenresField genresField = new GenresField("Genres", genres);

        getBinder().forField(genresField)
                .withValidator(list -> list.size() > 0, "Select genres")
                .bind(BookDto::getGenres, BookDto::setGenres);
        addComponent(genresField);
    }
}
