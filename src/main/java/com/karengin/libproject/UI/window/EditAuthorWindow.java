package com.karengin.libproject.UI.window;

import com.karengin.libproject.UI.form.AuthorEditForm;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.service.AuthorService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@ViewScope
public class EditAuthorWindow extends AbstractEditWindow<AuthorDto, AuthorService> {

    @Autowired
    public EditAuthorWindow(final AuthorEditForm editForm) {
        super(editForm);
    }
}
