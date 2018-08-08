package com.ardecs.springbootapp.client;

import com.google.gwt.aria.client.AlertdialogRole;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class GWTApp implements EntryPoint {
    public void onModuleLoad() {
        Label theGreeting = new Label("Hello World!");
        Button button = new Button("Hello!");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.alert("World!");
            }
        });
        RootPanel.get().add(button);
    }
}
