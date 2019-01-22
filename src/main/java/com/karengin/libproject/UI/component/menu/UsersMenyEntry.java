package com.karengin.libproject.UI.component.menu;

import com.karengin.libproject.UI.view.UsersView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class UsersMenyEntry extends ButtonMenuEntry {

    public UsersMenyEntry() {
        super("Users", UsersView.NAME);
    }
}
