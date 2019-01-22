package com.karengin.libproject.UI.view;

import com.karengin.libproject.UI.form.AuthorEditForm;
import com.karengin.libproject.UI.window.AbstractEditWindow;
import com.karengin.libproject.UI.window.EditAuthorWindow;
import com.karengin.libproject.UI.window.EditBookWindow;
import com.karengin.libproject.dto.AbstractDto;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.service.AbstractService;
import com.karengin.libproject.service.AuthorService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import static java.lang.Math.toIntExact;

@SpringView(name = AuthorsView.NAME)
@Component(AuthorsView.NAME)
public class AuthorsView extends AbstractView<AuthorDto, AuthorService>{

    public static final String NAME = "authors";

    public AuthorsView(final AuthorService service) {
        super(service);
    }

    @Override
    protected AbstractEditWindow<AuthorDto, AuthorService> getEditWindow(final AuthorDto dto, final AuthorService service) {
        return new EditAuthorWindow(new AuthorEditForm(dto, service));
    }

    @Override
    protected Class<AuthorDto> getDtoClass() {
        return AuthorDto.class;
    }
}
