package com.karengin.libproject.UI.component;

import com.karengin.libproject.UI.component.menu.MainMenu;
import com.vaadin.navigator.ViewDisplay;

public interface MainLayout extends Wrapper {

    MainMenu getMenu();

    ViewDisplay getViewContainer();
}
