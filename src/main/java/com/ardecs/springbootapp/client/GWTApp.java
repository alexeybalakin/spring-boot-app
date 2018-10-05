package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.FileDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;

import java.util.ArrayList;
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
    private final DateBox date = new DateBox();
    private FileUpload fileUpload = new FileUpload();
    private HorizontalPanel docControl = new HorizontalPanel();
    private TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(20, Style.Unit.PX);

    private Long id = -1L;

    private ListDataProvider<DocumentDTO> dataDocProvider;

    private UserDTO currentUser;

    private DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);

    public void onModuleLoad() {
        tabLayoutPanel.add(getUsersTabContent(), "Пользователи");
        tabLayoutPanel.add(getDocsTabContent(), "Документы");
        tabLayoutPanel.setHeight("420px");
        tabLayoutPanel.selectTab(0);
        RootPanel.get().add(tabLayoutPanel);
    }

    //создаем таблицу юзеров и панельку с кнопками управления
    private VerticalPanel getUsersTabContent() {
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

        VerticalPanel userPanel = new VerticalPanel();
        userPanel.add(userControl);
        userPanel.add(userTable);
        return userPanel;
    }

    //создаем таблицу документов и панельку с кнопками управления
    private VerticalPanel getDocsTabContent() {

        CellTable<DocumentDTO> docTable = new CellTable<>();
        dataDocProvider = createDocTable(docTable);;
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

        docControl.add(addDoc);
        docControl.add(editDoc);
        docControl.add(deleteDoc);
        docControl.setVisible(false);

        VerticalPanel docPanel = new VerticalPanel();
        docPanel.add(docControl);
        docPanel.add(docTable);
        return docPanel;
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
        Column<DocumentDTO, SafeHtml> fileColumn = new Column<DocumentDTO, SafeHtml>(
                new SafeHtmlCell()) {

            @Override
            public SafeHtml getValue(DocumentDTO doc) {
                SafeHtmlBuilder sb = new SafeHtmlBuilder();
                if (!doc.getFiles().isEmpty()) {
                    String file =  doc.getFiles().get(0).getName();
                    sb.appendHtmlConstant("<a href='"
                        + "/files/"
                        + file
                        +"'>"
                        + file
                        + "</a>");
                }
                return sb.toSafeHtml();
            }
        };

        table.addColumn(titleColumn, "Заголовок");
        table.addColumn(descriptionColumn, "Описание");
        table.addColumn(dateColumn, "Дата");
        table.addColumn(fileColumn, "Файл");
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
                    }
                });
                dialogBox.hide();
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
        date.setFormat(new DateBox.DefaultFormat(dateFormat));
        datePanel.add(date);
        dpanel.add(datePanel);

        //форма для загрузки файла
        final FormPanel form = createFileUploadForm();
        dpanel.add(form);

        HorizontalPanel dcontrol = new HorizontalPanel();
        dcontrol.add(new Button("Сохранить", new ClickHandler() {
            public void onClick(ClickEvent event) {
                List<FileDTO> fileList= new ArrayList<>();
                DocumentDTO newDoc = new DocumentDTO(id,  date.getValue(), title.getValue(), description.getValue(), fileList,currentUser);
                FileDTO fileDTO = new FileDTO(-1L, fileUpload.getFilename().substring(fileUpload.getFilename().lastIndexOf("\\")+1),newDoc);
                fileList.add(fileDTO);

                docService.saveWithFile(newDoc, new AsyncCallback<DocumentDTO>() {
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

    private FormPanel createFileUploadForm() {
        VerticalPanel panel = new VerticalPanel();
        final FormPanel form = new FormPanel();
        fileUpload = new FileUpload();
        fileUpload.getElement().setAttribute("name", "file");
        Button uploadButton = new Button("Загрузить файл");
        //pass action to the form to point to service handling file receiving operation.
        form.setAction("/upload");
        // set form to use the POST method, and multipart MIME encoding.
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        panel.add(fileUpload);
        panel.add(uploadButton);
        uploadButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String filename = fileUpload.getFilename();
                if (filename.length() == 0) {
                    Window.alert("Файл не выбран!");
                } else {
                    form.submit();
                }
            }
        });

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                // When the form submission is successfully completed, this
                //event is fired. Assuming the service returned a response
                //of type text/html, we can get the result text here
                //Window.alert(event.getResults());
                Window.alert("Файл загружен");
            }
        });
        panel.setSpacing(10);
        form.add(panel);
        return form;
    }
}