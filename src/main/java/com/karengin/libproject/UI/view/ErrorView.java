package com.karengin.libproject.UI.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = ErrorView.NAME)
public class ErrorView extends VerticalLayout implements View {

    public static final String NAME = "error";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(new Label("Access denied"));
    }
}
