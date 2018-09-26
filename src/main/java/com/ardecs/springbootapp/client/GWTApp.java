package com.ardecs.springbootapp.client;

import java.util.List;

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
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class GWTApp implements EntryPoint {

    private RestUserService userService = GWT.create(RestUserService.class);
    private RestDocService docService = GWT.create(RestDocService.class);

    private final TextBox login = new TextBox();
    private final TextBox password = new TextBox();
    private final TextBox name = new TextBox();

    private TabLayoutPanel content = new TabLayoutPanel(20, Style.Unit.PX);
    ;

    private Long id = -1L;

    public void onModuleLoad() {

        //создаем таблицу юзеров и добавляем на первую вкладку
        CellTable<User> userTable = new CellTable<>();
        ListDataProvider<User> dataProvider = createUserTable(userTable);
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
                User user = dataProvider.getList().get(index);
                userService.delete(user, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("error", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        dataProvider.getList().remove(index);
                    }
                });
            }
        });

        Button edit = new Button("Редактировать", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                User user = dataProvider.getList().get(userTable.getKeyboardSelectedRow());
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
        CellTable<Document> docTable = new CellTable<>();
        ListDataProvider<Document> dataDocProvider = createDocTable(docTable);
        VerticalPanel docPanel = new VerticalPanel();
        docPanel.add(docTable);
        content.add(docPanel, "Документы");

        content.setHeight("420px");
        content.selectTab(0);
        RootPanel.get().add(content);
    }

    private ListDataProvider<User> createUserTable(CellTable<User> table) {
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
        this.userService.list(new MethodCallback<List<User>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Ошибка при получении данных из таблицы users!");
            }

            @Override
            public void onSuccess(Method method, List<User> people) {
                dataProvider.getList().addAll(people);
            }
        });
        return dataProvider;
    }

    private ListDataProvider<Document> createDocTable(CellTable<Document> table) {
        TextColumn<Document> titleColumn = new TextColumn<Document>() {
            @Override
            public String getValue(Document doc) {
                return doc.getTitle();
            }
        };
        TextColumn<Document> descriptionColumn = new TextColumn<Document>() {
            @Override
            public String getValue(Document doc) {
                return doc.getDescription();
            }
        };
        TextColumn<Document> dateColumn = new TextColumn<Document>() {
            @Override
            public String getValue(Document doc) {
                return doc.getData().toString();
            }
        };
        TextColumn<Document> userColumn = new TextColumn<Document>() {
            @Override
            public String getValue(Document doc) {
                return doc.getFiles().get(0).getName() + "";
            }
        };
        table.addColumn(titleColumn, "Заголовок");
        table.addColumn(descriptionColumn, "Описание");
        table.addColumn(dateColumn, "Дата");
        table.addColumn(userColumn, "Файл");
        ListDataProvider<Document> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);
        this.docService.list(new MethodCallback<List<Document>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("Ошибка при получении данных из таблицы docs! " + exception.getCause().toString());
                GWT.log("error", exception);
            }

            @Override
            public void onSuccess(Method method, List<Document> documents) {
                dataProvider.getList().addAll(documents);
            }
        });
        return dataProvider;
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

                userService.save(newUser, new MethodCallback<User>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("error", exception);
                        Window.alert("Save error! ");
                    }

                    @Override
                    public void onSuccess(Method method, User user) {
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