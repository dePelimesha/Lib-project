package com.karengin.libproject.UI.view;

import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractView<T> extends VerticalLayout implements View {

    HorizontalLayout headerLayout = new HorizontalLayout();
    Button addButton = new Button("Add");
    Button editButton = new Button("Edit");
    Button deleteButton = new Button("Delete");
    TextField nameFilteringTextField = new TextField();

    List<T> dtoList = new ArrayList<>();
    DataProvider<T, String> dataProvider;
    Grid<T> grid;
    MultiSelectionModel<T> selectionModel;
    ConfigurableFilterDataProvider<T, Void, String> wrapper;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        fillHeader();
    }

    protected abstract void setEventListeners();

    private void fillHeader() {
        headerLayout.addComponent(addButton);
        headerLayout.addComponent(editButton);
        headerLayout.addComponent(deleteButton);
        headerLayout.addComponent(nameFilteringTextField);

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    void settingSelectionModel() {
        selectionModel.addMultiSelectionListener(selectionEvent -> {
            int selectedItemsCount = selectionEvent.getAllSelectedItems().size();
            if (selectedItemsCount == 0) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else if (selectedItemsCount == 1) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(true);
            }
        });
    }
}
