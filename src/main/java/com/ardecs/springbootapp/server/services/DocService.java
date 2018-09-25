package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.FileDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.entities.File;
import com.ardecs.springbootapp.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocService {

    @Autowired
    private DocumentRepository repository;

    @Transactional(readOnly = true)
    public List<DocumentDTO> list() {
        List<DocumentDTO> documents = new ArrayList<>();
        for(Document document : repository.findAll()){
            List<FileDTO> files = new ArrayList<>();
            for (File file : document.getFiles()){
                files.add(new FileDTO(file.getId(), file.getName()));
            }
            documents.add(new DocumentDTO(document.getId(), document.getData(), document.getTitle(),
                    document.getDescription(), files, new UserDTO(document.getUser().getId(), document.getUser().getLogin(),
                    document.getUser().getPassword(), document.getUser().getName())));
        }
        return documents;
    }

    public void delete(DocumentDTO document) {
    }

    public DocumentDTO save(DocumentDTO document) {
        return null;
    }


}
