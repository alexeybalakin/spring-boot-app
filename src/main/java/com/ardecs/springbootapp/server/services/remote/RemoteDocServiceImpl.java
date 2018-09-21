package com.ardecs.springbootapp.server.services.remote;

import com.ardecs.springbootapp.client.RemoteDocService;
import com.ardecs.springbootapp.entities.Document;
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
    public List<Document> list() {
        return docService.list();
    }

    @Override
    public void delete(Document document) {

    }

    @Override
    public Document save(Document document) {
        return null;
    }
}
