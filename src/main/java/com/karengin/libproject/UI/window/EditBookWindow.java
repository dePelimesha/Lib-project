package com.karengin.libproject.UI.window;

import com.karengin.libproject.UI.customField.GenresField;
import com.karengin.libproject.UI.form.AbstractEditForm;
import com.karengin.libproject.UI.form.BookEditForm;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.GenreService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringComponent
@ViewScope
public class EditBookWindow extends AbstractEditWindow<BookDto, BookService> {

    @Autowired
    public EditBookWindow(final BookEditForm bookEditForm) {
        super(bookEditForm);
    }
}
