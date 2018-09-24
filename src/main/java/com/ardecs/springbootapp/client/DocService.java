package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.entities.Document;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwt.doc_service")
public interface DocService extends RemoteService {
    List<DocumentDTO> list();

    void delete(DocumentDTO document);

    Document save(DocumentDTO document);
}
