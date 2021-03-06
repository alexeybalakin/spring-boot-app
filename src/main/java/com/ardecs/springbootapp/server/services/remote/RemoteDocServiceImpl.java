package com.ardecs.springbootapp.server.services.remote;

import com.ardecs.springbootapp.client.RemoteDocService;
import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.server.services.DocService;
import com.ardecs.springbootapp.server.util.ExtRemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoteDocServiceImpl extends ExtRemoteServiceServlet implements RemoteDocService {

    @Autowired
    DocService docService;

    @Override
    public List<DocumentDTO> list() {
        return docService.list();
    }

    @Override
    public List<DocumentDTO> listByUser(UserDTO user) {
        return docService.listByUser(user);
    }

    @Override
    public void delete(DocumentDTO document) {
        docService.delete(document);
    }

    @Override
    public DocumentDTO save(DocumentDTO document) {
        return docService.save(document);
    }

    @Override
    public DocumentDTO saveWithFile(DocumentDTO document) {
        return docService.saveWithFile(document);
    }
}
