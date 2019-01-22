package com.karengin.libproject.UI.view;

import com.karengin.libproject.UI.window.AbstractEditWindow;
import com.karengin.libproject.dto.AbstractDto;
import com.karengin.libproject.service.AbstractService;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractView<DTO extends AbstractDto, Service extends AbstractService>
        extends VerticalLayout implements View {

    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    private final TextField nameFilteringTextField = new TextField();

    private Service service;

    private Grid<DTO> grid;

    private ConfigurableFilterDataProvider<DTO, Void, String> wrapper;

    @Autowired
    public AbstractView(final Service service) {
        this.service = service;
        grid = new Grid<>(getDtoClass());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        fillHeader();
        setupGrid();
        setEventListeners();
    }

    private void setupGrid() {
        final DataProvider<DTO, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> service.findByFilterQueryWithPagination(query),
                query -> (int) service.findByFilterQueryWithPagination(query).count()
        );
        wrapper = dataProvider.withConfigurableFilter();
        grid.setDataProvider(wrapper);
        grid.getDataProvider().refreshAll();
        settingSelectionModel();
        addComponent(grid);
    }

    private void setEventListeners() {
        addButton.addClickListener(clickEvent -> {
            try {
                final Window window = getEditWindow(getDtoClass().newInstance(), getService());
                window.addCloseListener(closeEvent -> {
                    grid.getDataProvider().refreshAll();
                });
                getUI().addWindow(window);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Impossible to create instance of entity");
            }
        });

        editButton.addClickListener(clickEvent ->
                grid.getSelectedItems().forEach(dto -> {
                    final Window window = getEditWindow(dto, getService());
                    window.addCloseListener(closeEvent -> {
                        grid.getDataProvider().refreshAll();
                    });
                    getUI().addWindow(window);
                })
        );

        deleteButton.addClickListener(clickEvent -> {
            service.deleteAll(grid.getSelectedItems()).getBody();
            Notification.show("Items was deleted");
            grid.getDataProvider().refreshAll();
        });

        nameFilteringTextField.addValueChangeListener(valueChangeEvent -> {
            String filter = valueChangeEvent.getValue();
            if (filter.trim().isEmpty()) {
                filter = null;
            }
            wrapper.setFilter(filter);
        });
    }

    private void fillHeader() {
        headerLayout.addComponent(addButton);
        headerLayout.addComponent(editButton);
        headerLayout.addComponent(deleteButton);
        headerLayout.addComponent(nameFilteringTextField);

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addComponent(headerLayout);
    }

    private void settingSelectionModel() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(selectionEvent -> {
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

    protected abstract AbstractEditWindow<DTO, Service> getEditWindow(final DTO dto, final Service service);
    protected abstract Class<DTO> getDtoClass();

    public ConfigurableFilterDataProvider<DTO, Void, String> getFilteredDataProvider() {
        return wrapper;
    }

    public Grid<DTO> getGrid() {
        return grid;
    }

    public Service getService() {
        return service;
    }
}
