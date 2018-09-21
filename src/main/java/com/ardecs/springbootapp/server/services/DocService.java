package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.repositories.DocumentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocService {

    @Autowired
    private DocumentRepository repository;

    @Transactional(readOnly = true)
    public List<Document> list() {
        List<Document> list = repository.findAll();
        list.forEach(el -> Hibernate.initialize(el.getFiles()));

        return list;
    }


    public void delete(Document document) {

    }


    public Document save(Document document) {
        return null;
    }
}
