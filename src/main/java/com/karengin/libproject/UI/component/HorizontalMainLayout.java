package com.karengin.libproject.UI.component;

import com.karengin.libproject.UI.component.MainLayout;
import com.karengin.libproject.UI.component.menu.MainMenu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringViewDisplay
public class HorizontalMainLayout extends HorizontalLayout implements MainLayout, ViewDisplay {

    private final MainMenu menu;
    private Panel viewContainer;

    public HorizontalMainLayout(final MainMenu menu) {
        this.menu = menu;
    }

    @PostConstruct
    public void init() {
        //setSizeFull();
        viewContainer = new Panel();
        viewContainer.setWidth("1000px");
        viewContainer.setHeight("600px");
        addComponent(menu.getUnderlying());
        addComponents(viewContainer);
    }

    @Override
    public AbstractComponent getUnderlying() {
        return this;
    }

    @Override
    public MainMenu getMenu() {
        return menu;
    }

    @Override
    public ViewDisplay getViewContainer() {
        return this;
    }

    @Override
    public void showView(final View view) {
        viewContainer.setContent(view.getViewComponent());
    }
}
