package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.client.DocService;
import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.repositories.DocumentRepository;
import com.ardecs.springbootapp.server.util.ExtRemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocServiceImpl extends ExtRemoteServiceServlet implements DocService {

    @Autowired
    private DocumentRepository repository;

    @Override
    public List<Document> list() {
        return repository.findAll();
    }

    @Override
    public void delete(Document document) {

    }

    @Override
    public Document save(Document document) {
        return null;
    }
}
