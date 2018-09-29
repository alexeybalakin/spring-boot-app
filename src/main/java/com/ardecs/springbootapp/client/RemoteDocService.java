package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("gwt.doc_service")
public interface RemoteDocService extends RemoteService {
    List<DocumentDTO> list();

    List<DocumentDTO> listByUser(UserDTO user);

    void delete(DocumentDTO document);

    DocumentDTO save(DocumentDTO document);

    DocumentDTO saveWithFile(DocumentDTO data);
}
