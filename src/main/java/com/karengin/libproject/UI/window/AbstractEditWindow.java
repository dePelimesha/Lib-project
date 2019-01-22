package com.karengin.libproject.UI.window;

import com.karengin.libproject.UI.form.AbstractEditForm;
import com.karengin.libproject.dto.AbstractDto;
import com.karengin.libproject.service.AbstractService;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEditWindow<DTO extends AbstractDto, Service extends AbstractService>
        extends Window {

    @Autowired
    private AbstractEditForm<DTO,Service> editForm;

    public AbstractEditWindow(final AbstractEditForm<DTO,Service> editForm) {
        this.editForm = editForm;
        setContent(this.editForm);
    }

    public AbstractEditForm<DTO, Service> getEditForm() {
        return editForm;
    }
}
