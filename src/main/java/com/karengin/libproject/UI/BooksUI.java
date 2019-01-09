package com.karengin.libproject.UI;

import com.karengin.libproject.UI.view.AuthorsView;
import com.karengin.libproject.UI.view.BooksView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;

@SpringUI
@Theme("valo")
@SpringViewDisplay
public class BooksUI extends UI implements ViewDisplay{

    private HorizontalLayout mainLayout = new HorizontalLayout();
    private VerticalLayout viewButtonsLayout = new VerticalLayout();
    private Panel viewDisplay;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(mainLayout);
        mainLayout.addComponent(viewButtonsLayout);
        viewButtonsLayout.setWidth("150px");

        addViewButtons();
    }

    private void addViewButtons() {
        viewButtonsLayout.addComponent(createNavigationButton("Books", BooksView.NAME));
        viewButtonsLayout.addComponent(createNavigationButton("Authors", AuthorsView.NAME));

        viewDisplay = new Panel();
        viewDisplay.setWidth("1000px");
        mainLayout.addComponent(viewDisplay);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        viewDisplay.setContent((Component) view);
    }
}
