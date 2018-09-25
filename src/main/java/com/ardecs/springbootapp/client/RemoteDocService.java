package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.Document;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import javax.servlet.Servlet;
import java.util.List;

@RemoteServiceRelativePath("gwt.doc_service")
public interface RemoteDocService extends RemoteService {
    List<DocumentDTO> list();

    void delete(DocumentDTO document);

    DocumentDTO save(DocumentDTO document);
}