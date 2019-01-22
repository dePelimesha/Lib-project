package com.karengin.libproject.UI.form;

import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.service.AuthorService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.TextField;

@SpringComponent
@ViewScope
public class AuthorEditForm extends AbstractEditForm<AuthorDto, AuthorService> {

    public AuthorEditForm(final AuthorDto dto, final AuthorService service) {
        super(dto, service);
    }

    @Override
    protected void bindEntityFields() {
        final TextField nameField = new TextField("Title");
        getBinder().forField(nameField)
                .bind(AuthorDto::getName, AuthorDto::setName);
        addComponent(nameField);
    }
}
