package com.karengin.libproject.UI.component.menu;

import com.karengin.libproject.UI.view.AuthorsView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class AuthorsMenyEntry extends ButtonMenuEntry {
    public AuthorsMenyEntry() {
        super("Authors", AuthorsView.NAME);
    }
}
