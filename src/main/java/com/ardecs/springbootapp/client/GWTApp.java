package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.entities.User;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

public class GWTApp implements EntryPoint {

    private UserServiceAsync userService = GWT.create(UserService.class);

    private ListDataProvider<User> createTable(CellTable<User> table) {
        TextColumn<User> idColumn = new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getId().toString();
            }
        };
        TextColumn<User> loginColumn = new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getLogin();
            }
        };
        table.addColumn(idColumn, "Id");
        table.addColumn(loginColumn, "Login");
        final ListDataProvider<User> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);
        this.userService.list(new AsyncCallback<List<User>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<User> result) {
                dataProvider.getList().addAll(result);

            }
        });
        return dataProvider;
    }

    public void onModuleLoad() {
        CellTable<User> table = new CellTable<>();
        ListDataProvider<User> dataProvider = createTable(table);

        Label theGreeting = new Label("Hello World!");
        Button button = new Button("Hello!");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                userService.testMethod(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Error: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Window.alert(s);
                    }
                });
            }
        });
        RootPanel.get().add(button);
        RootPanel.get().add(table);
    }
}
