package com.karengin.libproject.UI.component.menu;

import com.karengin.libproject.UI.component.menu.MainMenu;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

@SpringComponent
@UIScope
public class VerticalMenu extends VerticalLayout implements MainMenu {

    private List<MenuEntry> menuEntries;

    public VerticalMenu(final List<MenuEntry> menuEntries) {
        this.menuEntries = menuEntries;
        menuEntries.forEach(menu -> {
            addComponent(menu.getUnderlying());
        });
        addComponent(new Button("Logout", clickEvent -> {
            Page.getCurrent().setLocation("/lib/rest/logout");
        }));
        this.setWidth("150px");
    }

    @Override
    public AbstractComponent getUnderlying() {
        return this;
    }

    @Override
    public List<MenuEntry> menuEntries() {
        return menuEntries;
    }
}
