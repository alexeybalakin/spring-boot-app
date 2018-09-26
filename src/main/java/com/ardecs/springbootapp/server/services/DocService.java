package com.ardecs.springbootapp.server.services;

import java.util.List;

import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.repositories.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocService {

    @Autowired
    private DocumentRepository repository;

    @Transactional(readOnly = true)
    public List<Document> list() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long docId) {
        repository.delete(docId);
    }

    @Transactional
    public Document save(Document document) {
        return null;
    }


}
