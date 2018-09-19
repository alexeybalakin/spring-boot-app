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

    private final TextBox login = new TextBox();
    private final TextBox password = new TextBox();
    private final TextBox name = new TextBox();

    private Long id = -1L;

    private ListDataProvider<User> createTable(CellTable<User> table) {
        TextColumn<User> loginColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getLogin();
            }
        };
        TextColumn<User> passwordColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getPassword();
            }
        };
        TextColumn<User> nameColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getName();
            }
        };
        table.addColumn(loginColumn, "Логин");
        table.addColumn(passwordColumn, "Пароль");
        table.addColumn(nameColumn, "Имя");
        ListDataProvider<User> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);
        this.userService.list(new AsyncCallback<List<User>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("error", throwable);
                Window.alert("error!");
            }
            @Override
            public void onSuccess(List<User> people) {
                dataProvider.getList().addAll(people);
            }
        });
        return dataProvider;
    }



    public void onModuleLoad() {
        CellTable<User> table = new CellTable<>();
        ListDataProvider<User> dataProvider = createTable(table);

        DialogBox dialog = editDialog(dataProvider);
        Button add = new Button("Добавить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                login.setValue("");
                password.setValue("");
                name.setValue("");
                id = -1L;
                dialog.center();
                dialog.show();
            }
        });

        Button delete = new Button("Удалить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final int index = table.getKeyboardSelectedRow();
                User user = dataProvider.getList().get(index);
                userService.delete(user, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        GWT.log("error", throwable);
                    }

                    @Override
                    public void onSuccess(Void v) {
                        dataProvider.getList().remove(index);
                    }
                });
            }
        });

        Button edit = new Button("Редактировать", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                User user = dataProvider.getList().get(table.getKeyboardSelectedRow());
                login.setValue(user.getLogin());
                password.setValue(user.getPassword());
                name.setValue(user.getName());
                id = user.getId();
                //Window.alert("id= " + id);
                dialog.center();
                dialog.show();
            }
        });

        HorizontalPanel control = new HorizontalPanel();
        control.add(add);
        control.add(edit);
        control.add(delete);
        VerticalPanel panel = new VerticalPanel();
        panel.add(control);
        panel.add(table);
        RootPanel.get().add(panel);
    }

    private DialogBox editDialog(ListDataProvider<User> dataProvider) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Добавить запись");
        dialogBox.setAnimationEnabled(true);
        VerticalPanel dpanel = new VerticalPanel();

        HorizontalPanel loginPanel = new HorizontalPanel();
        Label labelLogin = new Label("Логин");
        labelLogin.setWidth("100px");
        loginPanel.add(labelLogin);
        loginPanel.add(login);
        dpanel.add(loginPanel);

        HorizontalPanel pasPanel = new HorizontalPanel();
        Label labelPas = new Label("Пароль");
        labelPas.setWidth("100px");
        pasPanel.add(labelPas);
        pasPanel.add(password);
        dpanel.add(pasPanel);

        HorizontalPanel namePanel = new HorizontalPanel();
        Label labelName = new Label("Имя");
        labelName.setWidth("100px");
        namePanel.add(labelName);
        namePanel.add(name);
        dpanel.add(namePanel);

        HorizontalPanel dcontrol = new HorizontalPanel();
        dcontrol.add(new Button("Сохранить", new ClickHandler() {
            public void onClick(ClickEvent event) {
                User newUser = new User(id, login.getValue(), password.getValue(), name.getValue());
                //Window.alert(" user.id= " + newUser.getId() + " user.login = " + newUser.getLogin() + " pass = " + newUser.getPassword());

                userService.save(newUser, new AsyncCallback<User>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        GWT.log("error", throwable);
                        Window.alert("Save error! ");
                    }

                    @Override
                    public void onSuccess(User user) {
                        if (id != -1) {
                            dataProvider.getList().set(dataProvider.getList().indexOf(user), user);
                        } else {
                            dataProvider.getList().add(user);
                        }
                        dialogBox.hide();
                    }
                });
            }
        }));
        dcontrol.add(new Button("Отменить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                dialogBox.hide();
            }
        }));
        dpanel.add(dcontrol);
        dialogBox.setWidget(dpanel);
        return dialogBox;
    }
}
