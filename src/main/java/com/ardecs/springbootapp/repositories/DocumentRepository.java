package com.ardecs.springbootapp.repositories;

import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByUser(User user);


}
