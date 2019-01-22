package com.karengin.libproject.UI.form;

import com.karengin.libproject.dto.AbstractDto;
import com.karengin.libproject.service.AbstractService;
import com.vaadin.data.Binder;
import com.vaadin.data.ReadOnlyHasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEditForm<DTO extends AbstractDto, Service extends AbstractService>
        extends FormLayout {

    @Autowired
    private final Service service;
    private final DTO dto;
    private Binder<DTO> binder;

    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Button saveButton = new Button("Save");

    public AbstractEditForm(final DTO dto, final Service service) {
        this.dto = dto;
        this.service = service;

        binder = new Binder<>();
        binder.readBean(dto);
        bindID();
        bindEntityFields();
        setupButtons();
    }

    protected abstract void bindEntityFields();

    private void bindID() {
        final Label label = new Label();
        label.setCaption("Id");
        ReadOnlyHasValue<DTO> hasValue = new ReadOnlyHasValue<>(
                dto -> {
                    String labelContent = "-";
                    if (getDto() != null) {
                        labelContent = String.valueOf(getDto().getId());
                    }
                    label.setValue(labelContent);
                });
        binder.forField(hasValue).bind(dto -> dto, null);
        addComponent(label);
    }

    private void setupButtons() {
        saveButton.addClickListener(clickEvent -> {
            try {
                getBinder().writeBean(getDto());
                Notification.show((String) service.save(getDto()).getBody());
            } catch (ValidationException e) {
                Notification.show("Wrong value", Notification.Type.ERROR_MESSAGE);
            }
        });
        buttonsLayout.addComponent(saveButton);
        addComponent(buttonsLayout);
    }

    public DTO getDto() {
        return dto;
    }

    public Binder<DTO> getBinder() {
        return binder;
    }

    public Service getService() {
        return service;
    }
}
