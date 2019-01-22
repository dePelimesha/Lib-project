package com.karengin.libproject.UI.component.menu;

import com.karengin.libproject.UI.component.Wrapper;

public interface MenuEntry extends Wrapper {

    String getCaption();

    void onClick();
}
