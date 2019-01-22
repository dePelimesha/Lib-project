package com.karengin.libproject.UI.view;

import com.karengin.libproject.UI.window.AbstractEditWindow;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.service.UserService;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@SpringView(name = UsersView.NAME)
@Secured(value = "ROLE_ADMIN")
@Component(UsersView.NAME)
public class UsersView extends AbstractView<UsersDto, UserService> {

    public static final String NAME = "users";

    public UsersView(final UserService service) {
        super(service);
    }

    @Override
    protected AbstractEditWindow<UsersDto, UserService> getEditWindow(final UsersDto dto, final UserService service) {
        return null;
    }

    @Override
    protected Class<UsersDto> getDtoClass() {
        return UsersDto.class;
    }
}
