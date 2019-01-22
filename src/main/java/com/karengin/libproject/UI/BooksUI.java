package com.karengin.libproject.UI;

import com.karengin.libproject.UI.component.MainLayout;
import com.karengin.libproject.UI.view.AuthorsView;
import com.karengin.libproject.UI.view.BooksView;
import com.karengin.libproject.UI.view.ErrorView;
import com.karengin.libproject.UI.view.UsersView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class BooksUI extends UI {
    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private MainLayout mainLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(mainLayout.getUnderlying());
        navigator.init(this, mainLayout.getViewContainer());
        navigator.navigateTo(BooksView.NAME);
    }
}
