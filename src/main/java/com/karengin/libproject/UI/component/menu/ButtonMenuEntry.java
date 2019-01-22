package com.karengin.libproject.UI.component.menu;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class ButtonMenuEntry extends Button implements MenuEntry {
    private String viewName;

    @Autowired
    private ViewAccessControl accessControl;

    public ButtonMenuEntry(final String caption, final String viewName) {
        this.viewName = viewName;
        setCaption(caption);
        addClickListener(click -> onClick());
    }

    @Override
    public String getCaption() {
        return super.getCaption();
    }

    @Override
    public void onClick() {
        if (accessControl.isAccessGranted(UI.getCurrent(), viewName)) {
            UI.getCurrent().getNavigator().navigateTo(viewName);
        } else {
                Notification.show("Access denied", Notification.Type.WARNING_MESSAGE);
        }
    }

    @Override
    public AbstractComponent getUnderlying() {
        return this;
    }
}
