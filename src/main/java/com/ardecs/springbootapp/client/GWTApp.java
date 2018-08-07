package com.ardecs.springbootapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class GWTApp implements EntryPoint {
    public void onModuleLoad() {
        Label theGreeting = new Label("Hello World!");
        RootPanel.get().add(theGreeting);
    }
}
