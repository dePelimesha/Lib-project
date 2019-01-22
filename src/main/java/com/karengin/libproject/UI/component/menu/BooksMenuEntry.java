package com.karengin.libproject.UI.component.menu;

import com.karengin.libproject.UI.view.BooksView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class BooksMenuEntry extends ButtonMenuEntry {

    public BooksMenuEntry() {
        super("Books", BooksView.NAME);
    }
}
