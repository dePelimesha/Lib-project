package com.karengin.libproject.UI.view;

import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.service.AuthorService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@SpringView(name = AuthorsView.NAME)
public class AuthorsView extends AbstractView<AuthorDto>{

    public static final String NAME = "authors";

    @Autowired
    private AuthorService authorService;

    private List<AuthorDto> authors = new ArrayList<>();

    @PostConstruct
    void init() {
        grid = new Grid<>(AuthorDto.class);
        selectionModel = (MultiSelectionModel<AuthorDto>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
        settingSelectionModel();
        setEventListeners();
        this.addComponent(headerLayout);
        this.addComponent(grid);

        dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    authors = authorService.getAuthorsList().getBody();
                    return authors.stream();
                },
                query -> toIntExact(authorService.getAuthorsCount().getBody())
        );

        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void setEventListeners() {

    }
}
