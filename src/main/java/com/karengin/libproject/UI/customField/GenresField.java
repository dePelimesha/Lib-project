package com.karengin.libproject.UI.customField;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class GenresField extends CustomField<List<String>> {

    private List<String> value;
    private List<String> genres;

    private VerticalLayout verticalLayout = new VerticalLayout();
    private final ComboBox<String> genresComboBox = new ComboBox<>();
    private final Button addButton = new Button(VaadinIcons.PLUS);
    private ListDataProvider<String> genreDataProvider;

    public GenresField(String caption, List<String> genres) {
        this.setCaption(caption);
        this.genres = new ArrayList<>(genres);
    }

    @Override
    protected Component initContent() {
        genreDataProvider = DataProvider.ofCollection(genres);
        genreDataProvider.addFilter(genre -> {
            if (value == null) {
                return true;
            }
            return !value.contains(genre);
        });

        if (value != null) {
            value.forEach(genre ->
                    verticalLayout.addComponent(addGenreValue(genre))
            );
        }

        genresComboBox.setDataProvider(genreDataProvider);

        verticalLayout.addComponent(new HorizontalLayout(genresComboBox, addButton));
        addButton.setEnabled(false);
        addEventsListener();
        return verticalLayout;
    }

    private void addEventsListener() {
        addButton.addClickListener(clickEvent -> {
            final String selectedValue = genresComboBox.getValue();
            if (value == null) value = new ArrayList<>();
            value.add(selectedValue);
            setValue(value);
            genreDataProvider.refreshAll();
            genresComboBox.setValue(null);
            verticalLayout.addComponent(addGenreValue(selectedValue),value.size() - 1);
        });
        genresComboBox.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                addButton.setEnabled(false);
            } else {
                addButton.setEnabled(true);
            }
        });
    }

    private HorizontalLayout addGenreValue(final String genre) {
        return new HorizontalLayout(new Label(genre),
                new Button(VaadinIcons.CLOSE, clickEvent -> {
                    value.remove(genre);
                    setValue(value);
                    genreDataProvider.refreshAll();
                    verticalLayout.removeComponent(clickEvent.getButton().getParent());
                })
        );
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    protected void doSetValue(List<String> value) {
        this.value = new ArrayList<>(value);
    }
}