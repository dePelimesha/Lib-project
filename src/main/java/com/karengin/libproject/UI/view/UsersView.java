package com.karengin.libproject.UI.view;

import com.karengin.libproject.UI.window.AddUserWindow;
import com.karengin.libproject.UI.window.EditUserWindow;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.service.UserService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.lang.Math.toIntExact;

@SpringView(name = UsersView.NAME)
@Secured(value = "ROLE_ADMIN")
@Component(UsersView.NAME)
public class UsersView extends AbstractView<UsersDto> {

    public static final String NAME = "users";

    @Autowired
    private UserService userService;

    @PostConstruct
    void init() {
        grid = new Grid<>(UsersDto.class);
        selectionModel = (MultiSelectionModel<UsersDto>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
        settingSelectionModel();
        setEventListeners();

        this.addComponent(headerLayout);
        this.addComponent(grid);

        dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    String filter = query.getFilter().orElse(null);
                    if (filter == null) {
                        dtoList = userService.getAll().getBody();
                    } else {
                        dtoList = userService.findByLoginContainString(filter).getBody();
                    }
                    return dtoList.stream();
                },
                query -> {
                    String filter = query.getFilter().orElse(null);
                    return toIntExact(userService.usersCount(filter).getBody());
                }
        );
        wrapper = dataProvider.withConfigurableFilter();

        grid.setDataProvider(wrapper);
        grid.setWidth("800px");
    }

    @Override
    protected void setEventListeners() {
        addButton.addClickListener(clickEvent -> {
            final Window window = new AddUserWindow(userService);
            window.addCloseListener(closeEvent -> {
                dataProvider.refreshAll();
                selectionModel.deselectAll();
            });
            getUI().addWindow(window);
        });

        editButton.addClickListener(clickEvent ->
                grid.getSelectedItems().forEach(userDto -> {
                    final Window window = new EditUserWindow(userService, userDto);
                    window.addCloseListener(closeEvent -> {
                        dataProvider.refreshAll();
                        selectionModel.deselectAll();
                    });
                    getUI().addWindow(window);
                })
        );
    }
}
