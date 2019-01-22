package com.karengin.libproject.UI.view;

import com.karengin.libproject.UI.form.BookEditForm;
import com.karengin.libproject.UI.window.AbstractEditWindow;
import com.karengin.libproject.UI.window.EditBookWindow;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.GenreService;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringView(name = BooksView.NAME)
@Component(BooksView.NAME)
public class BooksView extends AbstractView<BookDto, BookService> {

    public static final String NAME = "books";

    //@Autowired
    public BooksView(final BookService service) {
        super(service);
    }

    @Override
    protected Class<BookDto> getDtoClass() {
        return BookDto.class;
    }

    @Override
    protected AbstractEditWindow<BookDto, BookService> getEditWindow(final BookDto dto,
                                                                     final BookService service) {
        return new EditBookWindow(new BookEditForm(dto, service));
    }
}
