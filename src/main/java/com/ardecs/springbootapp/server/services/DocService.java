package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.FileDTO;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.entities.File;
import com.ardecs.springbootapp.entities.User;
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

    @Transactional(readOnly = true)
    public List<DocumentDTO> listByUser(UserDTO userDTO){
        List<DocumentDTO> documents = new ArrayList<>();
        for(Document document : repository.findAllByUser(new User(userDTO))){
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
        repository.delete(document.getId());
    }

    public DocumentDTO save(DocumentDTO data) {
        Document document = new Document();
        document.setId(data.getId());
        document.setData(data.getData());
        document.setDescription(data.getDescription());
        document. setTitle(data.getTitle());
        repository.save(document);
        data.setId(document.getId());
        return data;
    }


}
