package com.karengin.libproject.UI.window;

import com.karengin.libproject.UI.customField.UserRoleField;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditUserWindow extends Window {
    private UsersDto user;
    private List<String> roles = new ArrayList<>();

    private final VerticalLayout mainLayout = new VerticalLayout();
    private final FormLayout addUserForm = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final TextField userNameTextField = new TextField("Username");
    private final PasswordField passwordField = new PasswordField("Password");
    private final UserRoleField userRoleField;
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private Binder<UsersDto> usersDtoBinder;

    public EditUserWindow(final UserService userService, final UsersDto user) {
        super("Edit user");
        this.user = user;
        this.setContent(mainLayout);
        mainLayout.addComponent(addUserForm);
        mainLayout.addComponent(buttonsLayout);

        fillRolesList(userService);

        userRoleField = new UserRoleField("Roles", roles);
        usersDtoBinder = new Binder<>();

        settingBinder(userService);
        addItemsOnForm();
        addEventListener(userService);
    }

    private void settingBinder(final UserService userService) {
        usersDtoBinder.forField(userNameTextField)
                .withValidator(login -> !userService.findByLogin(login, user).getBody()
                        , "Login already exists")
                .bind(UsersDto::getLogin, UsersDto::setLogin);
        usersDtoBinder.forField(passwordField)
                .withValidator(password -> password.length() >= 6, "Password too short")
                .bind(UsersDto::getPassword, UsersDto::setPassword);
        usersDtoBinder.forField(userRoleField)
                .withValidator(Objects::nonNull, "Choose role")
                .bind(UsersDto::getRole, UsersDto::setRole);
        usersDtoBinder.readBean(user);
    }

    private void fillRolesList(UserService userService) {
        userService.getAllRoles().getBody().forEach(role -> roles.add(role.getRole()));
    }

    private void addEventListener(UserService userService) {
        saveButton.addClickListener(clickEvent -> {
            try {
                usersDtoBinder.writeBean(user);
                Notification.show(userService.editUser(user).getBody());
                this.close();
            } catch (ValidationException e) {
                Notification.show("Wrong value", Notification.Type.ERROR_MESSAGE);
            }
        });
        cancelButton.addClickListener(clickEvent -> this.close());
    }

    private void addItemsOnForm() {
        addUserForm.addComponent(userNameTextField);
        addUserForm.addComponent(passwordField);
        addUserForm.addComponent(userRoleField);
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);
    }
}
