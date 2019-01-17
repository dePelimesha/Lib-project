package com.karengin.libproject.UI;

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

    private HorizontalLayout mainLayout = new HorizontalLayout();
    private VerticalLayout viewButtonsLayout = new VerticalLayout();
    private Panel viewDisplay;

    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private ViewAccessControl accessControl;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(mainLayout);
        mainLayout.addComponent(viewButtonsLayout);
        viewButtonsLayout.setWidth("150px");

        addViewButtons();
        navigator.init(this, viewDisplay);
        navigator.navigateTo(BooksView.NAME);
        //viewProvider.setAccessDeniedViewClass(ErrorView.class);
    }

    private void addViewButtons() {
        viewButtonsLayout.addComponent(createNavigationButton("Books", BooksView.NAME));
        viewButtonsLayout.addComponent(createNavigationButton("Authors", AuthorsView.NAME));
        viewButtonsLayout.addComponent(createNavigationButton("Users", UsersView.NAME));
        viewButtonsLayout.addComponent(new Button("Logout", clickEvent -> {
            Page.getCurrent().setLocation("/lib/rest/logout");
        }));

        viewDisplay = new Panel();
        viewDisplay.setWidth("1000px");
        mainLayout.addComponent(viewDisplay);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addClickListener(event -> {
            if (accessControl.isAccessGranted(this, viewName)) {
                navigator.navigateTo(viewName);
            } else {
                Notification.show("Access denied", Notification.Type.WARNING_MESSAGE);
            }
        });
        return button;
    }
}
