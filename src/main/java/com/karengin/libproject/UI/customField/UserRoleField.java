package com.karengin.libproject.UI.customField;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class UserRoleField extends CustomField<String> {

    private String value;
    private List<String> roles;

    private VerticalLayout verticalLayout = new VerticalLayout();
    private final RadioButtonGroup<String> roleRadioButton = new RadioButtonGroup<>();
    private ListDataProvider<String> roleDataProvider;

    public UserRoleField(final String caption,final List<String> roles) {
        this.setCaption(caption);
        this.roles = new ArrayList<>(roles);
    }

    @Override
    protected Component initContent() {
        roleDataProvider = DataProvider.ofCollection(roles);
        roleRadioButton.setDataProvider(roleDataProvider);
        if (value != null) {
            roleRadioButton.setSelectedItem(value);
        }

        roleRadioButton.addValueChangeListener(valueChangeEvent -> doSetValue(valueChangeEvent.getValue()));

        verticalLayout.addComponent(roleRadioButton);
        return verticalLayout;
    }

    @Override
    protected void doSetValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
