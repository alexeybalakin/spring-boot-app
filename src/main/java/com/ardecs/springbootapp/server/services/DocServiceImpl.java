package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.client.DocService;
import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.FileDTO;
import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.entities.File;
import com.ardecs.springbootapp.repositories.DocumentRepository;
import com.ardecs.springbootapp.server.util.ExtRemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class DocServiceImpl extends ExtRemoteServiceServlet implements DocService {

    @Autowired
    private DocumentRepository repository;

    @Override
     public List<DocumentDTO> list() {
        List<DocumentDTO> documents = new ArrayList<>();
        for(Document document : repository.findAll()){
            List<FileDTO> files = new ArrayList<>();
            for (File file : document.getFiles()){
                files.add(new FileDTO(file.getId(), file.getName()));
            }
            documents.add(new DocumentDTO(document.getId(), document.getData(), document.getTitle(), document.getDescription(), files));
        }
        return documents;
    }

    @Override
    public void delete(DocumentDTO document) {

    }

    @Override
    public Document save(DocumentDTO document) {
        return null;
    }
}
