package com.karengin.libproject.UI.window;

import com.karengin.libproject.UI.customField.GenresField;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.GenreService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ViewScope
public class EditBookWindow extends Window {

    private List<String> authors = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private BookDto book;

    private final FormLayout addBookForm = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final TextField bookIdTextField = new TextField("Id");
    private final TextField bookTitleTextField = new TextField("Title");
    private final TextField bookDescriptionTextFiled = new TextField("Description");
    private final ComboBox<String> authorsComboBox = new ComboBox<>("Author");
    private final GenresField genresField;
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private Binder<BookDto> bookDtoBinder;

    public EditBookWindow(final BookDto bookDto,
                          final BookService bookService,
                          final AuthorService authorService,
                          final GenreService genreService) {
        super("Edit book");
        this.book = bookDto;
        final VerticalLayout mainLayout = new VerticalLayout();
        this.setContent(mainLayout);
        mainLayout.addComponent(addBookForm);
        mainLayout.addComponent(buttonsLayout);
        fillAuthorsList(authorService);
        fillGenresList(genreService);

        genresField = new GenresField("Genres", genres);
        bookDtoBinder = new Binder<>();

        settingBinder();
        addItemsOnForm();
        addEventListener(bookService);
    }

    private void settingBinder() {
        bookDtoBinder.forField(bookIdTextField)
                .withConverter(Long::valueOf, String::valueOf)
                .bind(BookDto::getId, BookDto::setId);
        bookDtoBinder.forField(bookTitleTextField)
                .bind(BookDto::getTitle, BookDto::setTitle);
        bookDtoBinder.forField(bookDescriptionTextFiled)
                .bind(BookDto::getDescription, BookDto::setDescription);
        bookDtoBinder.forField(authorsComboBox)
                .withValidator(Objects::nonNull, "Author required")
                .bind(BookDto::getAuthor, BookDto::setAuthor);
        bookDtoBinder.forField(genresField)
                .withValidator(list -> list.size() > 0, "Select genres")
                .bind(BookDto::getGenres, BookDto::setGenres);
        bookDtoBinder.readBean(book);
    }

    private void addEventListener(final BookService bookService) {
        saveButton.addClickListener(clickEvent -> {
            try {
                bookDtoBinder.writeBean(book);
                Notification.show(bookService.changeBook(book).getBody());
                this.close();
            } catch (ValidationException e) {
                Notification.show("Wrong value", Notification.Type.ERROR_MESSAGE);
            }
        });
        cancelButton.addClickListener(clickEvent -> {
            bookDtoBinder.readBean(book);
            this.close();
        });
    }

    private void fillAuthorsList(final AuthorService authorService) {
        authorService.getAuthorsList().getBody()
                .forEach(authorDto -> authors.add(authorDto.getName()));
        ListDataProvider<String> authorDataProvider = DataProvider.ofCollection(authors);

        authorsComboBox.setDataProvider(authorDataProvider);
    }

    private void fillGenresList(final GenreService genreService) {
        genreService.getGenresList().getBody()
                .forEach(genreDto -> genres.add(genreDto.getGenre()));
    }

    private void addItemsOnForm() {
        bookIdTextField.setEnabled(false);
        addBookForm.addComponent(bookIdTextField);
        addBookForm.addComponent(bookTitleTextField);
        addBookForm.addComponent(bookDescriptionTextFiled);
        addBookForm.addComponent(authorsComboBox);
        addBookForm.addComponent(genresField);
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);
    }
}
