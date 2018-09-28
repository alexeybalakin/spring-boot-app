package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Date;
import java.util.List;

public class GWTApp implements EntryPoint {

    private RemoteUserServiceAsync userService = GWT.create(RemoteUserService.class);
    private RemoteDocServiceAsync docService = GWT.create(RemoteDocService.class);

    private final TextBox login = new TextBox();
    private final TextBox password = new TextBox();
    private final TextBox name = new TextBox();

    private final TextBox title = new TextBox();
    private final TextBox description = new TextBox();
    //private final TextBox date = new TextBox();
    private final DateBox date = new DateBox();

    private TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(20, Style.Unit.PX);;

    private Long id = -1L;

    private CellTable<DocumentDTO> docTable = new CellTable<>();
    private ListDataProvider<DocumentDTO> dataDocProvider;
    private VerticalPanel docPanel;
    private Panel docControl;

    private UserDTO currentUser;

    public void onModuleLoad() {

        //создаем таблицу юзеров и добавляем на первую вкладку
        CellTable<UserDTO> userTable = new CellTable<>();
        ListDataProvider<UserDTO> dataProvider = createUserTable(userTable);
        DialogBox userDialog = editUserDialog(dataProvider);
        Button add = new Button("Добавить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                login.setValue("");
                password.setValue("");
                name.setValue("");
                id = -1L;
                userDialog.center();
                userDialog.show();
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
                        Window.alert("Ошибка при удалении пользователя " + user.getName());
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
                userDialog.center();
                userDialog.show();
            }
        });
        HorizontalPanel userControl = new HorizontalPanel();
        userControl.add(add);
        userControl.add(edit);
        userControl.add(delete);
        VerticalPanel panel = new VerticalPanel();
        panel.add(userControl);
        panel.add(userTable);
        tabLayoutPanel.add(panel, "Пользователи");

        //создаем таблицу документов и добавляем на вторую вкладку
        dataDocProvider = createDocTable(docTable);

        docControl = createDocControlPanel(docTable, dataDocProvider);
        docControl.setVisible(false);

        docPanel = new VerticalPanel();
        docPanel.add(docControl);
        docPanel.add(docTable);
        tabLayoutPanel.add(docPanel, "Документы");

        tabLayoutPanel.setHeight("420px");
        tabLayoutPanel.selectTab(0);
        RootPanel.get().add(tabLayoutPanel);
    }

    private Panel createDocControlPanel(CellTable<DocumentDTO> docTable, ListDataProvider<DocumentDTO> dataDocProvider) {
        DialogBox docDialog = editDocDialog(dataDocProvider);
        Button addDoc = new Button("Добавить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                title.setValue("");
                description.setValue("");
                date.setValue(null);
                id = -1L;
                docDialog.center();
                docDialog.show();
            }
        });

        Button deleteDoc = new Button("Удалить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final int index = docTable.getKeyboardSelectedRow();
                DocumentDTO document = dataDocProvider.getList().get(index);
                docService.delete(document, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Ошибка при удалении документа " + document.getTitle());
                    }

                    @Override
                    public void onSuccess(Void v) {
                        dataDocProvider.getList().remove(index);
                    }
                });
            }
        });

        Button editDoc = new Button("Редактировать", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                DocumentDTO document = dataDocProvider.getList().get(docTable.getKeyboardSelectedRow());
                title.setValue(document.getTitle());
                description.setValue(document.getDescription());
                date.setValue(document.getData());
                id = document.getId();
                docDialog.center();
                docDialog.show();
            }
        });
        HorizontalPanel docControl = new HorizontalPanel();
        docControl.add(addDoc);
        docControl.add(editDoc);
        docControl.add(deleteDoc);
        return docControl;
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

        Cell<UserDTO> cell = new ActionCell<>("Документы", new ActionCell.Delegate<UserDTO>() {
            @Override
            public void execute(UserDTO userDTO) {
                   currentUser = userDTO;
                docService.listByUser(userDTO, new AsyncCallback<List<DocumentDTO>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Ошибка при получении документов пользователя " + userDTO.getName());
                    }

                    @Override
                    public void onSuccess(List<DocumentDTO> result) {
                        dataDocProvider.getList().clear();
                        dataDocProvider.getList().addAll(result);
                    }
                });
                docControl.setVisible(true);
                tabLayoutPanel.setTabText(1, "Документы пользователя " + currentUser.getName());
                tabLayoutPanel.selectTab(1);
            }
        });

        Column<UserDTO, UserDTO>  buttonColumn = new Column<UserDTO, UserDTO>(cell) {
            @Override
            public UserDTO getValue(UserDTO object) {
                return object;
            }
        };

        table.addColumn(loginColumn, "Логин");
        table.addColumn(passwordColumn, "Пароль");
        table.addColumn(nameColumn, "Имя");
        table.addColumn(buttonColumn,"Документы");
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

        DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);
        Column<DocumentDTO, Date> dateColumn = new Column<DocumentDTO, Date>(new DateCell(dateFormat)) {
            @Override
            public Date getValue(DocumentDTO object) {
                return object.getData();
            }
        };

        TextColumn<DocumentDTO> userColumn = new TextColumn<DocumentDTO>() {
            @Override
            public String getValue(DocumentDTO doc) {
                return doc.getUser().getName();
            }
        };
        table.addColumn(titleColumn, "Заголовок");
        table.addColumn(descriptionColumn, "Описание");
        table.addColumn(dateColumn, "Дата");
        table.addColumn(userColumn, "Владелец");
        ListDataProvider<DocumentDTO> documentDataProvider = new ListDataProvider<>();
        documentDataProvider.addDataDisplay(table);
        this.docService.list(new AsyncCallback<List<DocumentDTO>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Ошибка при получении данных из таблицы docs!");
            }
            @Override
            public void onSuccess(List<DocumentDTO> result) {
                documentDataProvider.getList().addAll(result);
            }
        });
        return documentDataProvider;
    }

    //диалоговое окно для добавления/редактирования пользователя
    private DialogBox editUserDialog(ListDataProvider<UserDTO> dataProvider) {
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

                userService.save(newUser, new AsyncCallback<UserDTO>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Save User error! ");
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

    //диалоговое окно для добавления/редактирования документа
    private DialogBox editDocDialog(ListDataProvider<DocumentDTO> dataProvider) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Добавить запись");
        dialogBox.setAnimationEnabled(true);
        VerticalPanel dpanel = new VerticalPanel();

        HorizontalPanel titlePanel = new HorizontalPanel();
        Label labelLogin = new Label("Заголовок");
        labelLogin.setWidth("100px");
        titlePanel.add(labelLogin);
        titlePanel.add(title);
        dpanel.add(titlePanel);

        HorizontalPanel descPanel = new HorizontalPanel();
        Label labelPas = new Label("Описание");
        labelPas.setWidth("100px");
        descPanel.add(labelPas);
        descPanel.add(description);
        dpanel.add(descPanel);

        HorizontalPanel datePanel = new HorizontalPanel();
        Label labelName = new Label("Дата");
        labelName.setWidth("100px");
        datePanel.add(labelName);
        datePanel.add(date);
        dpanel.add(datePanel);

        HorizontalPanel dcontrol = new HorizontalPanel();
        dcontrol.add(new Button("Сохранить", new ClickHandler() {
            public void onClick(ClickEvent event) {
                DocumentDTO newDoc = new DocumentDTO(id,  date.getValue(), title.getValue(), description.getValue(), currentUser);

                docService.save(newDoc, new AsyncCallback<DocumentDTO>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Save Document error! ");
                    }
                    @Override
                    public void onSuccess(DocumentDTO documentDTO) {
                        if (id != -1) {
                            dataDocProvider.getList().set(dataDocProvider.getList().indexOf(documentDTO), documentDTO);
                        } else {
                            dataDocProvider.getList().add(documentDTO);
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