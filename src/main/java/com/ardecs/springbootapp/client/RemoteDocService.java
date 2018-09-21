package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.entities.Document;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import javax.servlet.Servlet;
import java.util.List;

@RemoteServiceRelativePath("gwt.doc_service")
public interface RemoteDocService extends RemoteService {
    List<Document> list();

    void delete(Document document);

    Document save(Document document);
}
