package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.entities.User;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
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

    private RemoteUserServiceAsync userService = GWT.create(RemoteUserService.class);
    private RemoteDocServiceAsync docService = GWT.create(RemoteDocService.class);

    private final TextBox login = new TextBox();
    private final TextBox password = new TextBox();
    private final TextBox name = new TextBox();

    private TabLayoutPanel content = new TabLayoutPanel(20, Style.Unit.PX);;

    private Long id = -1L;

    public void onModuleLoad() {

        //создаем таблицу юзеров и добавляем на первую вкладку
        CellTable<UserDTO> userTable = new CellTable<>();
        ListDataProvider<UserDTO> dataProvider = createUserTable(userTable);
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
                final int index = userTable.getKeyboardSelectedRow();
                UserDTO user = dataProvider.getList().get(index);
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
                UserDTO user = dataProvider.getList().get(userTable.getKeyboardSelectedRow());
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
        panel.add(userTable);
        content.add(panel, "Пользователи");

        //создаем таблицу документов и добавляем на вторую вкладку
        CellTable<DocumentDTO> docTable = new CellTable<>();
        ListDataProvider<DocumentDTO> dataDocProvider = createDocTable(docTable);
        VerticalPanel docPanel = new VerticalPanel();
        docPanel.add(docTable);
        content.add(docPanel, "Документы");

        content.setHeight("420px");
        content.selectTab(0);
        RootPanel.get().add(content);
    }

    private ListDataProvider<UserDTO> createUserTable(CellTable<UserDTO> table) {
        TextColumn<UserDTO> loginColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getLogin();
            }
        };
        TextColumn<UserDTO> passwordColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getPassword();
            }
        };
        TextColumn<UserDTO> nameColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getName();
            }
        };
        table.addColumn(loginColumn, "Логин");
        table.addColumn(passwordColumn, "Пароль");
        table.addColumn(nameColumn, "Имя");
        ListDataProvider<UserDTO> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);
        this.userService.list(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Ошибка при получении данных из таблицы users!");
            }
            @Override
            public void onSuccess(List<UserDTO> people) {
                dataProvider.getList().addAll(people);
            }
        });
        return dataProvider;
    }

    private ListDataProvider<DocumentDTO> createDocTable(CellTable<DocumentDTO> table) {
        TextColumn<DocumentDTO> titleColumn = new TextColumn<DocumentDTO>() {
            @Override
            public String getValue(DocumentDTO doc) {
                return doc.getTitle();
            }
        };
        TextColumn<DocumentDTO> descriptionColumn = new TextColumn<DocumentDTO>() {
            @Override
            public String getValue(DocumentDTO doc) {
                return doc.getDescription();
            }
        };
        TextColumn<DocumentDTO> dateColumn = new TextColumn<DocumentDTO>() {
            @Override
            public String getValue(DocumentDTO doc) {
                return doc.getData().toString();
            }
        };
        TextColumn<DocumentDTO> userColumn = new TextColumn<DocumentDTO>() {
            @Override
            public String getValue(DocumentDTO doc) {
                return doc.getId() + "";
            }
        };
        table.addColumn(titleColumn, "Заголовок");
        table.addColumn(descriptionColumn, "Описание");
        table.addColumn(dateColumn, "Дата");
        table.addColumn(userColumn, "Владелец(user_id)");
        ListDataProvider<DocumentDTO> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);
        this.docService.list(new AsyncCallback<List<DocumentDTO>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Ошибка при получении данных из таблицы docs!");
            }
            @Override
            public void onSuccess(List<DocumentDTO> documents) {
                dataProvider.getList().addAll(documents);
            }
        });
        return dataProvider;
    }

    private DialogBox editDialog(ListDataProvider<UserDTO> dataProvider) {
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
                UserDTO newUser = new UserDTO(id, login.getValue(), password.getValue(), name.getValue());
                //Window.alert(" user.id= " + newUser.getId() + " user.login = " + newUser.getLogin() + " pass = " + newUser.getPassword());

                userService.save(newUser, new AsyncCallback<UserDTO>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        GWT.log("error", throwable);
                        Window.alert("Save error! ");
                    }
                    @Override
                    public void onSuccess(UserDTO user) {
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